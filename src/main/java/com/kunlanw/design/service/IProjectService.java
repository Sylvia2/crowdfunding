package com.kunlanw.design.service;

import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ContractProject;
import com.kunlanw.design.model.FundEntity;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.model.ProjectFilter;

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
    boolean createProject(Project entity) throws Exception;

    List<Project> getAll() throws Exception;

    List<Project> getByFilter(ProjectFilter filter) throws Exception;

    Project updateProject(int id, Project project) throws Exception;

    Project auditProject(int id) throws Exception;

    boolean fundProject(int id, FundEntity entity)throws Exception;

    ContractProject getProjectOnContract(int id)throws Exception;



}
