package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.ProjectDao;
import com.kunlanw.design.dao.UserDao;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.service.IProjectService;
import com.kunlanw.design.until.Constant;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private UserDao userDao;

    /**
     * 获取全部众筹项目
     *
     * @return
     */
    @Override
    public List<ProjectEntity> getAllProject() {
        List<ProjectEntity> resList = new ArrayList<>();
        try {
            List<Project> projects = this.projectDao.findAll();
            if (projects != null && !projects.isEmpty()) {
                for (Project item : projects) {
                    ProjectEntity entity = this.domain2dto(item);
                    resList.add(entity);
                }
            }
        } catch (Exception e) {
            //TODO need add log
            return resList;
        }
        return resList;
    }

    /**
     * 根据项目id获取详情
     *
     * @param id
     * @return
     */
    @Override
    public ProjectEntity getByID(int id) {
        try {
            Project project = this.projectDao.findById(id).get();
            ProjectEntity res = this.domain2dto(project);
            return res;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * 创建众筹项目
     *
     * @param entity
     * @return
     */
    @Override
    public ProjectEntity createProject(ProjectEntity entity) {
        try {
            Project project=this.dto2domain(entity);
            Project res=this.projectDao.save(project);
            return this.domain2dto(res);
        } catch (Exception e) {
            return null;
        }
    }




    /**
     * @param item
     * @return
     */
    private ProjectEntity domain2dto(Project item) {
        if (item != null) {
            ProjectEntity entity = new ProjectEntity();
            entity.setProjectID(item.getProjectID());
            entity.setUserID(item.getUserID());
            entity.setWalletID(item.getWalletID());
            entity.setDeadline(item.getDeadline());
            entity.setProjectAmount(item.getProjectamount());
            entity.setProjectName(item.getProjectname());
            entity.setDesc(item.getDesc());
            entity.setStatus(item.getStatus());
            entity.setType(item.getType());
            return entity;
        }
        return null;
    }

    /**
     * @param entity
     * @return
     */
    private Project dto2domain(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }
        Project res = new Project();
        res.setStatus(entity.getStatus());
        res.setUserID(entity.getUserID());
        res.setWalletID(entity.getWalletID());
        res.setDeadline(entity.getDeadline());
        res.setDesc(entity.getDesc());
        res.setProjectname(entity.getProjectName());
        res.setProjectamount(entity.getProjectAmount());
        res.setType(entity.getType());
        res.setDatachange_createtime(new Date());
        res.setDatachange_lasttime(new Date());
        return res;
    }
}
