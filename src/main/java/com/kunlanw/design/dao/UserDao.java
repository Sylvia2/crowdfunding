package com.kunlanw.design.dao;

import com.kunlanw.design.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> ,CrudRepository<User,Integer> {

}
