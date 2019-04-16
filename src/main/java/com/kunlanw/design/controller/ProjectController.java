package com.kunlanw.design.controller;

import com.alibaba.fastjson.JSON;
import com.kunlanw.design.contract.IFundService;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.*;
import com.kunlanw.design.service.ILoggerService;
import com.kunlanw.design.service.IProjectService;
import com.kunlanw.design.until.Constant;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IFundService fundService;
    @Autowired
    private ILoggerService loggerService;


    /**
     * 根据ID获取项目详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String get(@PathVariable int id, Model model) {
        try {
            ProjectEntity project = this.projectService.getByID(id);
            model.addAttribute("projectDetail", project);

            return "project";
        } catch (Exception e) {
            return "common/404";
        }
    }

    /**
     * 创建项目，数据库，合约
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody ProjectEdit project, HttpSession session) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            int userid = (Integer) session.getAttribute(Constant.User_Session);
            boolean res = this.projectService.createProject(project, userid);
            result.setResult(res);
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部项目
     *
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAll() {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<ProjectEntity> list = this.projectService.getAll();
            if (list != null && !list.isEmpty()) {
                result.setResult(list);
            }
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 根据条件筛选项目
     *
     * @param filter
     * @return
     */
    @RequestMapping(value = "/getByFilter", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult getByFilter(@RequestBody ProjectFilter filter) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<ProjectEntity> list = this.projectService.getByFilter(filter);
            if (list != null && !list.isEmpty()) {
                result.setResult(list);
            }
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新项目，只可更新项目的desc
     *
     * @param project
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateProject/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseResult updateProject(@PathVariable int id, @RequestBody ProjectEdit project) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            Project res = this.projectService.updateProject(id, project);
            if (res != null) {
                result.setResult(res);
            }
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 审核项目
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/auditProject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult auditProjectByID(@PathVariable int id) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            Project project = this.projectService.auditProject(id);
            if (project != null) {
                result.setResult(project);
            }
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 参与众筹
     *
     * @param fundEntity
     * @return
     */
    @RequestMapping(value = "/fundProject", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseResult fundProject(@RequestBody FundEntity fundEntity, HttpSession session) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            int userid = (Integer) session.getAttribute(Constant.User_Session);
            fundEntity.setUserID(userid);
            boolean res = this.projectService.fundProject(fundEntity);
            result.setResult(res);
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取项目的实时金额，获取链上数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProjectOnContract/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getProjectOnContract(@PathVariable int id) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            ContractProject projectOnContract = this.projectService.getProjectOnContract(id);
            result.setResult(projectOnContract);
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * 获取用户创建的众筹list
     *
     * @return
     */
    @RequestMapping(value = "/getCreatedProjects", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getCreatedProjects(Model model, HttpSession session
    ) {
        ResponseResult result = new ResponseResult();
        int userId = (Integer) session.getAttribute(Constant.User_Session);
        result.setCode(0);
        try {
            List<ProjectEntity> createdProjects = this.projectService.getCreatedProject(userId);
            if (createdProjects != null) {
                result.setResult(createdProjects);
            }
            return result;
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "/getJoinProjects", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getJoinProjects(Model model, HttpSession session) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            int userId = (Integer) session.getAttribute(Constant.User_Session);
            List<ProjectEntity> joinProjects = this.loggerService.getByUserid(userId);
            if (joinProjects != null) {
                result.setResult(joinProjects);
            }
            return result;
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "/getProjectsByStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getProjectsByStatus() {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<ProjectEntity> list = this.projectService.getAll();
            List<ViewStatus> res = new ArrayList<>();
            if(list.size()>0){
                for(int i=0;i<4;i++){
                    res.add(this.getViewStatus(i,list));
                }
            }
            result.setResult(res);
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private ViewStatus  getViewStatus(int i,List<ProjectEntity> list){
        int num = list.stream().filter(p -> p.getStatus() == i).collect(Collectors.toList()).size();
        return new ViewStatus(this.getStatus(i),num);
    }


    private String getStatus(int i){
        String status="";
        switch (i){
            case 0:
                status="审核中";
                break;
            case 1:
                status="进行中";
                break;
            case 2:
                status="成功";
                break;
            case 3:
                status="失败";
                break;
        }

        return status;
    }


    /**
     * 1=创业
     * 2=扶贫
     * 3=环境保护
     * 4=动物保护
     *
     * @return
     */
    @RequestMapping(value = "/getProjectsByType", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getProjectByType() {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<ProjectEntity> list = this.projectService.getAll();
            List<ViewType> res = new ArrayList<>();
            if (list.size() > 0) {
                for (int i = 1; i < 5; i++) {
                    ViewType temp=this.getViewType(i,list);
                    if(temp==null){
                        continue;
                    }
                    res.add(this.getViewType(i, list));
                }
            }
            result.setResult(res);
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private ViewType getViewType(int i, List<ProjectEntity> list) {
        int num = list.stream().filter(p -> p.getType() == i).collect(Collectors.toList()).size();
        if(num==0){
            return null;
        }
        return new ViewType(this.getType(i), num, this.getFormateDouble(num*1.0 / list.size()));
    }

    private String getType(int i) {
        String type = "其他";
        switch (i) {
            case 1:
                type = "创业";
                break;
            case 2:
                type = "扶贫";
                break;
            case 3:
                type = "环境保护";
                break;
            case 4:
                type = "动物保护";
                break;
            case 5:
                type = "其他";
                break;
        }
        return type;
    }

    private double getFormateDouble(double old) {
        BigDecimal db = new BigDecimal(old);
        return db.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    @RequestMapping(value = "/getIncreaseProjects",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getIncreaseProjects(){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            List<ProjectEntity> list=this.projectService.getAll();
            if(list.size()>0){
                List<ViewIncrease> res=new ArrayList<>();
                Map<String,List<ProjectEntity>> temp=list.stream().collect(Collectors.groupingBy(ProjectEntity::getDataCreateTime));
                for (String  key : temp.keySet()) {
                    res.add(new ViewIncrease(key.substring(5),temp.get(key).size()));
                }
                result.setResult(res);
            }
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
