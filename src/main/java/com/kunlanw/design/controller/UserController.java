package com.kunlanw.design.controller;

import com.kunlanw.design.domain.User;
import com.kunlanw.design.model.LoginEntity;
import com.kunlanw.design.model.UserEntity;
import com.kunlanw.design.service.IUserService;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult register(@RequestBody User user){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            User temp=this.userService.getByEmail(user.getUseremail());
            if(temp!=null){
                throw new Exception("该电子邮箱已经被注册过了");
            }
            int userid=this.userService.createUser(user);
            UserEntity entity=this.userService.getUserById(userid);
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
    public ResponseResult login(@RequestBody LoginEntity loginEntity){
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
            UserEntity entity=this.userService.getUserById(user.getUserid());
            result.setResult(entity);
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
    public ResponseResult updateUser(@RequestBody User user){
        ResponseResult result=new ResponseResult();
        result.setCode(0);
        try{
            UserEntity entity=this.userService.updateUser(user);
            result.setResult(entity);
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }



}
