package com.kunlanw.design.service;

import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.WalletEntity;

import java.util.List;

public interface IWalletService {

    /**
     * 用户创建钱包
     * @param wallet
     * @return
     */
    boolean createWallet(WalletEntity wallet);

    /**
     * 用户获取自己的钱包
     * @param userId
     * @return
     */
    List<WalletEntity> getWalletByUserId(int userId) throws Exception;

    /**
     * 删除钱包
     * @param walletId
     * @return
     * @throws Exception
     */
    boolean deleteWalletById(int walletId)throws Exception;
}
