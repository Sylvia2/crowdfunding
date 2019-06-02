package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.LogMapper;
import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.domain.Log;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.LogEntity;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.model.ViewType;
import com.kunlanw.design.service.ILoggerService;
import com.kunlanw.design.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.math.BigDecimal;
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
            List<LogEntity> res = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (Log item : list) {
                    LogEntity entity = new LogEntity();
                    entity.setLogId(item.getLogid());
                    Project project = this.projectMapper.selectByPrimaryKey(item.getProjectid());
                    if (project != null) {
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
    private double getFormateDouble(double old) {
        BigDecimal db = new BigDecimal(old);
        return db.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public List<ViewType> getPaidType(int userid) throws Exception {
        try {
            List<ViewType> res = new ArrayList<>();
            List<Log> list = this.logMapper.getLogsByUserid(userid);
            List<LogEntity> temp = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (Log item : list) {
                    LogEntity entity = new LogEntity();
                    entity.setLogId(item.getLogid());
                    Project project = this.projectMapper.selectByPrimaryKey(item.getProjectid());
                    if (project != null) {
                        entity.setProjectId(item.getProjectid());
                        entity.setProjectName(project.getProjectname());
                        entity.setTypeId(project.getType());
                    }
                    entity.setAmount(item.getAmount());
                    entity.setFromAddress(item.getWalletid());
                    entity.setDate(this.formateDate(item.getDatachangeLasttime()));
                    temp.add(entity);
                }
            }
            long amount1 = temp.stream().filter(x -> x.getTypeId() == 1).mapToLong(LogEntity::getAmount).sum();
            long amount2 = temp.stream().filter(x -> x.getTypeId() == 2).mapToLong(LogEntity::getAmount).sum();
            long amount3 = temp.stream().filter(x -> x.getTypeId() == 3).mapToLong(LogEntity::getAmount).sum();
            long amount4 = temp.stream().filter(x -> x.getTypeId() == 4).mapToLong(LogEntity::getAmount).sum();
            long amount5 = temp.stream().filter(x -> x.getTypeId() == 5).mapToLong(LogEntity::getAmount).sum();
            long all = amount1 + amount2 + amount3 + amount4 + amount5;
            if (all > 0) {

                if (amount1 > 0) {
                    res.add(new ViewType("创业", (int) amount1, this.getFormateDouble(amount1 * 1.0 / all)));
                }
                if (amount2 > 0) {
                    res.add(new ViewType("扶贫", (int) amount2, this.getFormateDouble(amount2 * 1.0 / all)));
                }
                if (amount3 > 0) {
                    res.add(new ViewType("环境保护", (int) amount3, this.getFormateDouble(amount3 * 1.0 / all)));
                }
                if (amount4 > 0) {
                    res.add(new ViewType("动物保护", (int) amount4, this.getFormateDouble(amount4 * 1.0 / all)));
                }
                if (amount5 > 0) {
                    res.add(new ViewType("其他", (int) amount5, this.getFormateDouble(amount5 * 1.0 / all)));
                }
            }
            return res;
        } catch (Exception e) {
            throw new Exception("获取分类项目金额失败");
        }
    }

    private String formateDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = format.format(date);
        return dateTime;

    }
}
