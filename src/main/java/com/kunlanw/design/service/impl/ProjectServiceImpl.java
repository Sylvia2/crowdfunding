package com.kunlanw.design.service.impl;

import com.kunlanw.design.contract.IFundService;
import com.kunlanw.design.dao.LogMapper;
import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.dao.UserMapper;
import com.kunlanw.design.dao.WalletMapper;
import com.kunlanw.design.domain.Log;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.domain.User;
import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.*;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Resource
    private ProjectMapper projectMapper;
    @Autowired
    private IFundService fundService;
    @Resource
    private WalletMapper walletMapper;
    @Resource
    private LogMapper logMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ProjectEntity getByID(int id) {
        try {
            Project project = this.projectMapper.selectByPrimaryKey(id);
            if (project == null) {
                return null;
            }
            ProjectEntity projectEntity = this.domain2model(project);
            return projectEntity;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 创建项目，数据库备份，数据上链
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public boolean createProject(ProjectEdit entity, int userid) throws Exception {
        boolean res = false;
        try {
            //status：0待审核 1进行中 2成功 3失败
            Project project = new Project();
            project.setUserid(userid);
            project.setDeadline(entity.getDeadline());
            project.setProjectname(entity.getName());
            project.setDesc(entity.getDesc());
            project.setProjectamount(entity.getAmount());
            project.setStatus((short) 0);
            project.setType(entity.getType());
            project.setWalletid(entity.getWalletAddress());
            project.setDatachangeLasttime(new Date());
            project.setDatachangeCreatetime(new Date());
            //数据库备份
            this.projectMapper.insertSelective(project);
            //上链
            Wallet wallet = this.walletMapper.selectByPrimaryKey(entity.getWalletAddress());
            res = this.fundService.createProject(entity.getAmount().toBigInteger(), wallet.getAddress());
        } catch (Exception e) {
//            throw new Exception("创建项目失败");
            res = false;
        }
        return res;
    }

    @Override
    public List<ProjectEntity> getAll() throws Exception {
        try {
            List<Project> projects = this.projectMapper.findAll();
            List<ProjectEntity> list = new ArrayList<>();
            if (projects == null || projects.isEmpty()) {
                return null;
            }
            for (Project item : projects) {
                ProjectEntity entity = this.domain2model(item);
                list.add(entity);
            }
            return list;
        } catch (Exception e) {
            throw new Exception("获取全部项目失败");
        }
    }

    @Override
    public List<ProjectEntity> getByFilter(ProjectFilter filter) throws Exception {
        try {
            List<Project> list = this.projectMapper.findByFilter(filter);
            List<ProjectEntity> res = new ArrayList<>();
            if (list == null || list.isEmpty()) {
                return null;
            }
            for (Project item : list) {
                ProjectEntity entity = this.domain2model(item);
                res.add(entity);
            }
            return res;
        } catch (Exception e) {
            throw new Exception("根据条件筛选项目失败");
        }
    }

    /**
     * 只有创建者可以更新项目：desc,name,deadline,type
     *
     * @param id
     * @param project
     * @return
     */
    @Override
    public Project updateProject(int id, ProjectEdit project) throws Exception {
        try {
            Project entity = this.projectMapper.selectByPrimaryKey(id);
            if (entity == null) {
                return null;
            }

            entity.setDesc(project.getDesc());
            entity.setProjectname(project.getName());
            entity.setDatachangeLasttime(new Date());
            this.projectMapper.updateByPrimaryKeySelective(entity);
            return this.projectMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new Exception("更新项目失败" + id);
        }
    }

    /**
     * 审核项目，更新项目状态
     * status：0待审核 1进行中 2成功 3失败
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Project auditProject(int id) throws Exception {
        try {
            Project project = this.projectMapper.selectByPrimaryKey(id);
            if (project == null) {
                return null;
            }
            project.setStatus((short) 1);
            this.projectMapper.updateByPrimaryKeySelective(project);
            return this.projectMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new Exception("审核失败");
        }
    }

    /**
     * 参与众筹，更新log表，与合约交互
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public boolean fundProject(FundEntity entity) throws Exception {
        try {
            ContractProject contractProject = this.fundService.getProjectOnContract(entity.getAddress_to());
            Project project = this.projectMapper.selectByPrimaryKey(entity.getProjectId());
            if (contractProject.getRealtimeAmount().longValue() < project.getProjectamount().longValue()) {
                Log log = new Log();
                log.setAmount(entity.getAmount().longValue());
                log.setProjectid(entity.getProjectId());
                log.setUserid(entity.getUserID());
                log.setDatachangeCreatetime(new Date());
                log.setDatachangeLasttime(new Date());
                log.setWalletid(entity.getFromID());
                this.logMapper.insertSelective(log);
                Wallet wallet = this.walletMapper.selectByPrimaryKey(entity.getFromID());
                entity.setAddress_from(wallet.getAddress());
                //与合约交互
                boolean res = this.fundService.fund(entity.getAddress_to(), entity.getAddress_from(), entity.getKey(), entity.getAmount().toBigInteger());
                return res;
            }
            if (entity.getAmount().longValue() >= (project.getProjectamount().longValue() - contractProject.getRealtimeAmount().longValue())) {
                project.setStatus((short) 2);
                this.projectMapper.updateByPrimaryKey(project);
            }
            return false;
        } catch (Exception e) {
            throw new Exception("参与众筹失败" + e.getMessage());
        }
    }

    /**
     * 获取项目的实时众筹金额
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ContractProject getProjectOnContract(int id) throws Exception {
        try {

            Project project = this.projectMapper.selectByPrimaryKey(id);
            Wallet wallet = this.walletMapper.selectByPrimaryKey(project.getWalletid());
            ContractProject contractProject = this.fundService.getProjectOnContract(wallet.getAddress());
            return contractProject;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ProjectEntity> getCreatedProject(int userid) throws Exception {
        try {
            List<Project> list = this.projectMapper.findByUserID(userid);
            List<ProjectEntity> res = new ArrayList<>();
            if (list == null || list.isEmpty()) {
                return null;
            }
            for (Project item : list) {
                ProjectEntity entity = this.domain2model(item);
                res.add(entity);
            }
            return res;

        } catch (Exception e) {
            return null;
        }
    }


    private String formateDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = format.format(date);
        return dateTime;

    }

    @Override
    public ProjectEntity domain2model(Project project) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectid(project.getProjectid());
        entity.setProjectamount(project.getProjectamount());
        entity.setProjectname(project.getProjectname());
        entity.setDateName(this.formateDate(project.getDeadline()));
        entity.setWalletid(project.getWalletid());
        Wallet wallet = this.walletMapper.selectByPrimaryKey(project.getWalletid());
        entity.setWalletAddress(wallet.getAddress());
        entity.setWalletid(project.getWalletid());
        User user = this.userMapper.selectByPrimaryKey(project.getUserid());
        entity.setUsername(user.getUsername());
        entity.setDeadline(project.getDeadline());
        entity.setStatus(project.getStatus());
        entity.setDesc(project.getDesc());
        entity.setType(project.getType());
        entity.setUserid(project.getUserid());
        entity.setDataCreateTime(this.formateDate(project.getDatachangeCreatetime()));
        return entity;
    }


}
