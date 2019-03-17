package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.ProjectMapper;
import com.kunlanw.design.dao.UserMapper;
import com.kunlanw.design.dao.WalletMapper;
import com.kunlanw.design.domain.Project;
import com.kunlanw.design.domain.User;
import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.ProjectEntity;
import com.kunlanw.design.model.UserEntity;
import com.kunlanw.design.service.IProjectService;
import com.kunlanw.design.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Autowired
    private IProjectService projectService;


    @Override
    public int createUser(User user) throws Exception {
        try {
            user.setDatachangeCreatetime(new Date());
            user.setDatechangeLasttime(new Date());
            int res = this.userMapper.insertSelective(user);
            if (res > 0) {
                User user1 = this.userMapper.findByEmail(user.getUseremail());
                return user1.getUserid();
            }
            return 0;
        } catch (Exception e) {
            throw new Exception("注册用户失败");
        }
    }


    @Override
    public User getByEmail(String email) throws Exception {
        try {
            User user = this.userMapper.findByEmail(email);
            return user;
        } catch (Exception e) {
            throw new Exception("该账号未注册");
        }
    }


    @Override
    public void updateUser(UserEntity user) throws Exception {
        try{
            User temp=new User();
            temp.setUserid(user.getUserid());
            temp.setUsername(user.getName());
            temp.setUseremail(user.getEmail());
            temp.setSex(user.getSex());
            temp.setDatechangeLasttime(new Date());
            this.userMapper.updateByPrimaryKeySelective(temp);
        }catch (Exception e){
            throw new Exception("更新用户信息失败");
        }
    }

    @Override
    public UserEntity getUserDetail(int userid) throws Exception {
        try {
            UserEntity entity = new UserEntity();
            User user = this.userMapper.selectByPrimaryKey(userid);
            entity.setName(user.getUsername());
            entity.setEmail(user.getUseremail());
            entity.setUserid(user.getUserid());
            entity.setSex(user.getSex());
            List<Wallet> wallets = this.walletMapper.findByUserID(userid);
            if (wallets != null && !wallets.isEmpty()) {
                entity.setWallets(wallets);
            }
            return entity;
        } catch (Exception e) {

            return null;
        }
    }
}
