package com.kunlanw.design.service;

import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.*;

import java.math.BigInteger;
import java.util.List;

public interface IProjectService {

    /**
     * 根据ID获取项目详情
     *
     * @param id
     * @return
     */
    ProjectEntity getByID(int id);

    /**
     * 创建项目
     *
     * @param entity
     */
    boolean createProject(ProjectEdit entity,int userid) throws Exception;

    List<ProjectEntity> getAll() throws Exception;

    List<ProjectEntity> getByFilter(ProjectFilter filter) throws Exception;

    Project updateProject(int id, ProjectEdit project) throws Exception;

    Project auditProject(int id) throws Exception;

    boolean fundProject(FundEntity entity)throws Exception;

    ContractProject getProjectOnContract(int id)throws Exception;


    List<ProjectEntity> getCreatedProject(int userid)throws Exception;

    ProjectEntity domain2model(Project project);

}
