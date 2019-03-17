package com.kunlanw.design.service;


import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectEntity;

import java.util.List;

public interface ILoggerService {
    List<ProjectEntity> getByUserid(int userid)throws Exception;
}
