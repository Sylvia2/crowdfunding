package com.kunlanw.design.service.impl;

import com.kunlanw.design.dao.WalletMapper;
import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.geom.AreaOp;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletSereviceImpl implements IWalletService {

    @Resource
    private WalletMapper walletMapper;

    @Override
    public boolean createWallet(Wallet wallet) {
        try{
            List<Wallet> wallets=this.walletMapper.findByUserID(wallet.getUserid());
            List<Wallet> list=wallets.stream().filter(p->p.getAddress()==wallet.getAddress()).collect(Collectors.toList());
            if(list!=null){
                throw new Exception("该钱包地址已存在");
            }
            wallet.setDatachangeCreatetime(new Date());
            wallet.setDatachangeLasttime(new Date());
            this.walletMapper.insertSelective(wallet);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Wallet> getWalletByUserId(int userId) throws Exception {
        try{
            List<Wallet> wallets=this.walletMapper.findByUserID(userId);
            return wallets;
        }catch (Exception e ){
            throw new Exception("获取用户的钱包地址失败");
        }
    }

    @Override
    public boolean deleteWalletById(int walletId) throws Exception {
        try{
            this.walletMapper.deleteByPrimaryKey(walletId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
