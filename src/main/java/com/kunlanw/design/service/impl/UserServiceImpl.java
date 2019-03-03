package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.dao.UserMapper;
import com.kunlanw.design.dao.WalletMapper;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.domain.User;
import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.LoginEntity;
import com.kunlanw.design.model.ProjectFilter;
import com.kunlanw.design.model.UserEntity;
import com.kunlanw.design.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ReportAsSingleViolation;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private WalletMapper walletMapper;
    @Resource
    private ProjectMapper projectMapper;


    @Override
    public int createUser(User user) throws Exception {
        try{
            user.setDatachangeCreatetime(new Date());
            user.setDatechangeLasttime(new Date());
            int res=this.userMapper.insertSelective(user);
            if(res>0){
                User user1=this.userMapper.findByEmail(user.getUseremail());
                return user1.getUserid();
            }
            return 0;
        }catch (Exception e){
         throw new Exception("注册用户失败");
        }
    }

    @Override
    public UserEntity getUserById(int userid) throws Exception {
        try{
            User user=this.userMapper.selectByPrimaryKey(userid);
            List<Wallet> wallets=this.walletMapper.findByUserID(userid);
            List<Project> projects=this.projectMapper.findByUserID(userid);
            UserEntity userEntity=new UserEntity();
            userEntity.setUserid(userid);
            userEntity.setEmail(user.getUseremail());
            userEntity.setName(user.getUsername());
            userEntity.setSex(user.getSex()!=null?user.getSex():0);
            if(wallets!=null&&!wallets.isEmpty()){
                userEntity.setWallets(wallets);
            }
            if(projects!=null&&!projects.isEmpty()){
                userEntity.setProjects(projects);
            }
            return userEntity;
        }catch (Exception e){
            throw new Exception("获取用户详情失败");
        }
    }

    @Override
    public User getByEmail(String email) throws Exception {
        try{
            User user=this.userMapper.findByEmail(email);
            return user;
        }catch (Exception e){
            throw new Exception("该账号未注册");
        }
    }


    @Override
    public UserEntity updateUser(User user) throws Exception {
        try{
            user.setDatechangeLasttime(new Date());
            this.userMapper.updateByPrimaryKeySelective(user);
            return this.getUserById(user.getUserid());
        }catch (Exception e){
            throw new Exception("更新用户信息失败");
        }
    }
}
