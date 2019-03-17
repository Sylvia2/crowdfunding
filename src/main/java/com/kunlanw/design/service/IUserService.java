package com.kunlanw.design.service;

import com.kunlanw.design.domain.User;
import com.kunlanw.design.model.LoginEntity;
import com.kunlanw.design.model.UserEntity;

import java.util.concurrent.ExecutionException;

public interface IUserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    int createUser(User user) throws Exception;

    /**
     *
     * @param email
     * @return
     */
    User getByEmail(String email) throws Exception;

    void updateUser(UserEntity user)throws Exception;

    UserEntity getUserDetail(int userid)throws Exception;
}
