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
import com.kunlanw.design.model.ContractProject;
import com.kunlanw.design.model.FundEntity;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.model.ProjectFilter;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
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
        try{
            Project project=this.projectMapper.selectByPrimaryKey(id);
            ProjectEntity projectEntity=new ProjectEntity();
            projectEntity.setProjectid(project.getProjectid());
            projectEntity.setDesc(project.getDesc());
            projectEntity.setDeadline(project.getDeadline()==null?new Date():project.getDeadline());
            projectEntity.setProjectamount(project.getProjectamount());
            projectEntity.setProjectname(project.getProjectname());
            projectEntity.setStatus(project.getStatus());
            projectEntity.setUserid(project.getUserid());
            projectEntity.setWalletid(project.getWalletid());
            projectEntity.setType(project.getType());
            projectEntity.setUsername("五十资本");
            projectEntity.setWalletAddress("x432h4b242h4b2kj4n31k4b1b");
           // projectEntity.setUsername(this.userMapper.selectByPrimaryKey(project.getUserid()).getUsername());
            //projectEntity.setWalletAddress(this.walletMapper.selectByPrimaryKey(project.getWalletid()).getAddress());
            return projectEntity;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 创建项目，数据库备份，数据上链
     * @param entity
     * @throws Exception
     */
    @Override
    public boolean createProject(Project entity) throws Exception {
        try{
            //status：0待审核 1进行中 2成功 3失败
            entity.setStatus((short)0);
            entity.setDatachangeCreatetime(new Date());
            entity.setDatachangeLasttime(new Date());
            //数据库备份
            int res=this.projectMapper.insertSelective(entity);
            //上链
            Wallet wallet=this.walletMapper.selectByPrimaryKey(entity.getWalletid());
            return this.fundService.createProject(entity.getProjectamount().toBigInteger(),wallet.getAddress());
        }catch (Exception e){
            throw new Exception("创建项目失败");
        }
    }

    @Override
    public List<Project> getAll() throws Exception {
        try{
            List<Project> list=this.projectMapper.findAll();
            return list;
        }catch (Exception e){
            throw new Exception("获取全部项目失败");
        }
    }

    @Override
    public List<Project> getByFilter(ProjectFilter filter) throws Exception {
        try{
            List<Project> list=this.projectMapper.findByFilter(filter);
            return list;
        }catch (Exception e){
            throw new Exception("根据条件筛选项目失败");
        }
    }

    /**
     * 只有创建者可以更新项目：desc,name,deadline,type
     * @param id
     * @param project
     * @return
     */
    @Override
    public Project updateProject(int id, Project project) throws Exception {
        try{
            Project entity=this.projectMapper.selectByPrimaryKey(id);
            if(entity==null){
                return null;
            }
            entity.setType(project.getType());
            entity.setDesc(project.getDesc());
            entity.setDeadline(project.getDeadline());
            entity.setProjectname(project.getProjectname());
            entity.setDatachangeLasttime(new Date());
            this.projectMapper.updateByPrimaryKeySelective(entity);
            return this.projectMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            throw new Exception("更新项目失败"+id);
        }
    }

    /**
     * 审核项目，更新项目状态
     * status：0待审核 1进行中 2成功 3失败
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Project auditProject(int id) throws Exception {
        try{
            Project project=this.projectMapper.selectByPrimaryKey(id);
            if(project==null){
                return null;
            }
            project.setStatus((short)1);
            this.projectMapper.updateByPrimaryKeySelective(project);
            return this.projectMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            throw new Exception("审核失败");
        }
    }

    /**
     * 参与众筹，更新log表，与合约交互
     * @param id
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public boolean fundProject(int id, FundEntity entity) throws Exception {
        try{
            Log log=new Log();
            log.setAmount(entity.getAmount().longValue());
            log.setProjectid(id);
            log.setUserid(entity.getUserID());
            log.setDatachangeCreatetime(new Date());
            log.setDatachangeLasttime(new Date());
            this.logMapper.insertSelective(log);
            //与合约交互
            boolean res=this.fundService.fund(entity.getAddress_to(),entity.getAddress_from(),entity.getAmount());
            return res;
        }catch (Exception e){
            throw new Exception("参与众筹失败"+e.getMessage());
        }
    }

    /**
     * 获取项目的实时众筹金额
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ContractProject getProjectOnContract(int id) throws Exception {
        try{

            Project project=this.projectMapper.selectByPrimaryKey(id);
            Wallet wallet=this.walletMapper.selectByPrimaryKey(project.getWalletid());
            ContractProject  contractProject=this.fundService.getProjectOnContract(wallet.getAddress());
            return contractProject;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }






}
