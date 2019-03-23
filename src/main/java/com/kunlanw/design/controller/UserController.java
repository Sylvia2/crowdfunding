package com.kunlanw.design.controller;

import com.kunlanw.design.domain.User;
import com.kunlanw.design.model.LogEntity;
import com.kunlanw.design.model.LoginEntity;
import com.kunlanw.design.model.UserEntity;
import com.kunlanw.design.service.ILoggerService;
import com.kunlanw.design.service.IUserService;
import com.kunlanw.design.until.Constant;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILoggerService loggerService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult register(@RequestBody User user,HttpSession session){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            User temp=this.userService.getByEmail(user.getUseremail());
            if(temp!=null){
                throw new Exception("该电子邮箱已经被注册过了");
            }
            this.userService.createUser(user);
            User entity=this.userService.getByEmail(user.getUseremail());
            session.setAttribute(Constant.User_Session,entity.getUserid());
            result.setResult(entity);
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 用户登录
     * @param loginEntity
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestBody LoginEntity loginEntity, HttpSession session){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            User user=this.userService.getByEmail(loginEntity.getEmail());
            if(user==null){
                throw new Exception("该用户尚未注册");
            }
            if(!user.getUserpassword().equalsIgnoreCase(loginEntity.getPwd())){
                throw new Exception("密码不正确");
            }
//            UserEntity entity=this.userService.getUserById(user.getUserid());
            session.setAttribute(Constant.User_Session,user.getUserid());
            result.setResult(user.getUserid());
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新账户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateUser(@RequestBody UserEntity user,HttpSession session){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            int userid=(Integer) session.getAttribute(Constant.User_Session);
            user.setUserid(userid);
            this.userService.updateUser(user);
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.setAttribute(Constant.User_Session,null);
        return "login";

    }

    @RequestMapping(value = "/userDetail",method = RequestMethod.GET)
    public String userDetail(HttpSession session, Model model){
        try{
            int userid=(Integer)session.getAttribute(Constant.User_Session);
            UserEntity entity=this.userService.getUserDetail(userid);
            if(entity!=null){
                model.addAttribute("details",entity);
            }
            return "userDetail";
        }catch (Exception e){
            return "common/404";
        }
    }

    @RequestMapping(value = "/logDetails",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getLogDetails(HttpSession session){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            int userid=(Integer)session.getAttribute(Constant.User_Session);
            List<LogEntity> res=this.loggerService.getLogsByUserid(userid);
            result.setResult(res);
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return  result;
    }



}
