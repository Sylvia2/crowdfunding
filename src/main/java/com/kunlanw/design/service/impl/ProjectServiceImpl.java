package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.ProjectDao;
import com.kunlanw.design.dao.UserDao;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @param id
     * @return
     */
    @Override
    public ProjectEntity getByID(int id) {
        try{
            Project project=this.projectDao.getOne(id);
            ProjectEntity res=this.domain2dto(project);
            return res;
        }catch (Exception e){

            return null;
        }
    }

    /**
     * 获取众筹项目的状态
     * @param status
     * @return
     */
    private String getStatus(short status) {
        switch (status) {
            case 1:
                return "成功";
            case 2:
                return "失败";
            case 3:
                return "待审批";
            case 4:
                return "进行中";
            default:
                return "";
        }
    }

    /**
     * 获取众筹项目类型
     * @param type
     * @return
     */
    private String getType(int type){
        switch (type){
            case 1:
                return "捐款";
            case 2:
                return "创业项目";
            default:
                return "";
        }
    }

    private ProjectEntity domain2dto(Project item){
        if(item!=null){
            ProjectEntity entity=new ProjectEntity();
            entity.setProjectID(item.getProjectID());
            entity.setUserID(item.getUserID());
            entity.setWalletID(item.getWalletID());
            entity.setDeadline(item.getDeadline());
            entity.setProjectAmount(item.getProjectAmount());
            entity.setProjectName(item.getProjectName());
            entity.setDesc(item.getDesc());
            entity.setStatus(this.getStatus(item.getStatus()));
            entity.setType(this.getType(item.getType()));
            return entity;
        }
        return null;
    }
}
