package com.kunlanw.design.service.impl;

import com.kunlanw.design.contract.IFundService;
import com.kunlanw.design.dao.WalletMapper;
import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.WalletEntity;
import com.kunlanw.design.service.IWalletService;
import com.kunlanw.design.until.ListUility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.geom.AreaOp;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletSereviceImpl implements IWalletService {

    @Resource
    private WalletMapper walletMapper;
    @Autowired
    private IFundService fundService;

    @Override
    public boolean createWallet(WalletEntity wallet) {
        try {
            List<Wallet> wallets = this.walletMapper.findByUserID(wallet.getUserid());
            if (wallets != null && !wallets.isEmpty()) {
                List<Wallet> list = wallets.stream().filter(p -> p.getAddress().equalsIgnoreCase(wallet.getAddress())).collect(Collectors.toList());
                if (list.size() > 0) {
                    throw new Exception("该钱包地址已存在");
                }
            }

            Wallet temp = new Wallet();
            temp.setUserid(wallet.getUserid());
            temp.setAddress(wallet.getAddress());
            temp.setDatachangeLasttime(new Date());
            temp.setDatachangeCreatetime(new Date());
            this.walletMapper.insertSelective(temp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<WalletEntity> getWalletByUserId(int userId) throws Exception {
        try {
            List<WalletEntity> res = new ArrayList<>();
            List<Wallet> wallets = this.walletMapper.findByUserID(userId);
            if (wallets != null && !wallets.isEmpty()) {
                for (Wallet item : wallets) {
                    WalletEntity entity = new WalletEntity();
                    entity.setAddress(item.getAddress());
                    entity.setId(item.getWalletid());
                    entity.setAmount(this.fundService.getWalletAmount(item.getAddress()) == null ? BigDecimal.ZERO : this.fundService.getWalletAmount(item.getAddress()));
                    res.add(entity);
                }
            }
            return res;
        } catch (Exception e) {
            throw new Exception("获取用户的钱包地址失败");
        }
    }

    @Override
    public boolean deleteWalletById(int walletId) throws Exception {
        try {
            this.walletMapper.deleteByPrimaryKey(walletId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
