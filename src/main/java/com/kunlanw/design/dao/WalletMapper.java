package com.kunlanw.design.dao;

import com.kunlanw.design.domain.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Integer walletid);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Integer walletid);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
}