package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectFilter;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.*;
import java.util.List;

@Mapper
public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectid);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projectid);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Project> findAll();

    List<Project> findByFilter(ProjectFilter filter);

    List<Project> findByUserID(Integer userid);
}