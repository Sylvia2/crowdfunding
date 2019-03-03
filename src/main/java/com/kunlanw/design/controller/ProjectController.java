package com.kunlanw.design.controller;

import com.kunlanw.design.contract.IFundService;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.model.ContractProject;
import com.kunlanw.design.model.FundEntity;
import com.kunlanw.design.model.ProjectFilter;
import com.kunlanw.design.service.IProjectService;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IFundService fundService;

    /**
     * 根据ID获取项目详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable int id) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            Project project = this.projectService.getByID(id);
            if (project != null) {
                result.setResult(project);
            }
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage("获取项目详情失败" + id);
        }
        return result;
    }

    /**
     * 创建项目，数据库，合约
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult create(@RequestBody Project project) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            boolean res=this.projectService.createProject(project);
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
    public ResponseResult getAll() {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<Project> list = this.projectService.getAll();
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
    public ResponseResult getByFilter(@RequestBody ProjectFilter filter) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            List<Project> list = this.projectService.getByFilter(filter);
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
    @RequestMapping(value = "/updateProject/{id}", method = RequestMethod.POST)
    public ResponseResult updateProject(@PathVariable int id, @RequestBody Project project) {
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
     * @param id
     * @param fundEntity
     * @return
     */
    @RequestMapping(value = "/fundProject/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseResult fundProject(@PathVariable int id, @RequestBody FundEntity fundEntity){
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            boolean res=this.projectService.fundProject(id,fundEntity);
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProjectOnContract/{id}",method = RequestMethod.GET)
    public ResponseResult getProjectOnContract(@PathVariable int id){
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        try {
            ContractProject projectOnContract=this.projectService.getProjectOnContract(id);
            result.setResult(projectOnContract);
            result.setMessage("successful");
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }





}
