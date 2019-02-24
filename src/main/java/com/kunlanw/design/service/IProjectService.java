package com.kunlanw.design.service;

import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectEntity;

import java.util.List;

public interface IProjectService {
    List<ProjectEntity> getAllProject();

    ProjectEntity getByID(int id);

    ProjectEntity createProject(ProjectEntity entity);
}
