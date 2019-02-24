package com.kunlanw.design.controller;

import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.service.IProjectService;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    /**
     * 获取全部众筹项目
     * @return
     */
    @RequestMapping(value = "/getAllProject",method = RequestMethod.GET)
    public ResponseResult getAll(){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        result.setMessage("");
        try{
            List<ProjectEntity> res=this.projectService.getAllProject();
            result.setResult(res);
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage("获取众筹项目失败");
        }
        return result;
    }

    /**
     * 根据项目ID获取详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProjectByID/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseResult getProjectByID(@PathVariable int id){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        result.setMessage("");
        try{
            ProjectEntity entity=this.projectService.getByID(id);
            if(entity!=null){
                result.setResult(entity);
            }
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage("根据项目ID获取详情失败");
        }
        return result;
    }

}
