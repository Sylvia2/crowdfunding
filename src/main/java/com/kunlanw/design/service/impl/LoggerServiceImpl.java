package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.LogMapper;
import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.domain.Log;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.LogEntity;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.service.ILoggerService;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl implements ILoggerService {

    @Resource
    private LogMapper logMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Autowired
    private IProjectService projectService;

    @Override
    public List<ProjectEntity> getByUserid(int userid) throws Exception {
        List<Integer> logs = this.logMapper.getByUserid(userid);
        if (logs == null || logs.isEmpty()) {
            return null;
        }
        List<ProjectEntity> reslist = new ArrayList<>();
        for (Integer item : logs) {
            Project project = this.projectMapper.selectByPrimaryKey(item);
            if (project == null) {
                continue;
            }
            ProjectEntity entity = this.projectService.domain2model(project);
            reslist.add(entity);
        }
        return reslist;
    }

    @Override
    public List<LogEntity> getLogsByUserid(int userid) throws Exception {
        try {
            List<Log> list = this.logMapper.getLogsByUserid(userid);
            List<LogEntity> res=new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for(Log item:list){
                    LogEntity entity=new LogEntity();
                    entity.setLogId(item.getLogid());
                    Project project=this.projectMapper.selectByPrimaryKey(item.getProjectid());
                    if(project!=null){
                        entity.setProjectId(item.getProjectid());
                        entity.setProjectName(project.getProjectname());
                    }
                    entity.setAmount(item.getAmount());
                    entity.setFromAddress(item.getWalletid());
                    entity.setDate(this.formateDate(item.getDatachangeLasttime()));
                    res.add(entity);
                }
            }
            return res;
        } catch (Exception e) {
            throw new Exception("获取交易详情失败");
        }
    }

    private String formateDate(Date date) {
        if(date==null){
            date=new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = format.format(date);
        return dateTime;

    }
}
