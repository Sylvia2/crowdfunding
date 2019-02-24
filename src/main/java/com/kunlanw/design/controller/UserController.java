package com.kunlanw.design.controller;

import java.util.Date;
import java.util.List;
import com.kunlanw.design.dao.UserDao;
import com.kunlanw.design.domain.User;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserDao userDao;

    @RequestMapping("/getUsers")
    public List<User> getDbType(){
        List<User> res=this.userDao.findAll();
        return res;

    }

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public User save(@RequestBody User user){
        user.setDatachange_createtime(new Date());
        user.setDatachange_lastTime(new Date());
        return this.userDao.save(user);
    }

    /**
     * TODO list
     * 1.登录验证，就是要放到session中
     * 2.注册用户
     */



}
