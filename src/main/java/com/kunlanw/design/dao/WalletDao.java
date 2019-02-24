package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletDao extends JpaRepository<Wallet,Integer>,CrudRepository<Wallet,Integer> {
}
