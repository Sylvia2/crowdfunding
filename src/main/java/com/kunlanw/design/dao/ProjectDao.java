package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<Project,Integer> ,CrudRepository<Project,Integer> {

}
