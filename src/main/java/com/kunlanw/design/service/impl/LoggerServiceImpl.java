package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.LogMapper;
import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.service.ILoggerService;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl implements ILoggerService {

    @Resource
    private LogMapper  logMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Autowired
    private IProjectService projectService;

    @Override
    public List<ProjectEntity> getByUserid(int userid) throws Exception {
        List<Integer> logs=this.logMapper.getByUserid(userid);
        if(logs==null||logs.isEmpty()){
            return null;
        }
        List<ProjectEntity> reslist=new ArrayList<>();
        for(Integer item:logs){
            Project project=this.projectMapper.selectByPrimaryKey(item);
            if(project==null){
                continue;
            }
            ProjectEntity entity=this.projectService.domain2model(project);
            reslist.add(entity);
        }
        return reslist;
    }
}
