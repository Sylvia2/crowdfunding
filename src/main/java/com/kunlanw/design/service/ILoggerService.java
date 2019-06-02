package com.kunlanw.design.service;


import com.kunlanw.design.model.LogEntity;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.model.ViewType;

import java.util.List;

public interface ILoggerService {
    List<ProjectEntity> getByUserid(int userid)throws Exception;

    List<LogEntity> getLogsByUserid(int userid) throws Exception;

    List<ViewType> getPaidType(int userid)throws Exception;
}
