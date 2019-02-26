package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectid);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projectid);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}