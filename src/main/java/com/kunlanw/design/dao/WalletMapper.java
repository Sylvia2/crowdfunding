package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Wallet;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WalletMapper {
    int deleteByPrimaryKey(Integer walletid);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Integer walletid);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);

    List<Wallet> findByUserID(Integer userid);
}