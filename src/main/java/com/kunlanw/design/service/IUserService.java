package com.kunlanw.design.service;

import com.kunlanw.design.domain.User;
import com.kunlanw.design.model.LoginEntity;
import com.kunlanw.design.model.UserEntity;

public interface IUserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    int createUser(User user) throws Exception;

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserEntity getUserById(int id) throws Exception;

    /**
     *
     * @param email
     * @return
     */
    User getByEmail(String email) throws Exception;

    UserEntity updateUser(User user)throws Exception;

}
