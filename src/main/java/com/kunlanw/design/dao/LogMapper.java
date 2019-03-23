package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {
    int deleteByPrimaryKey(Integer logid);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer logid);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Integer> getByUserid(int userid);

    List<Log> getLogsByUserid(int userid);
}