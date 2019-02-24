package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends JpaRepository<Log,Integer>,CrudRepository<Log,Integer> {
}
