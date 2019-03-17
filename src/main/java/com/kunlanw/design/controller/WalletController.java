package com.kunlanw.design.controller;

import com.kunlanw.design.domain.Wallet;
import com.kunlanw.design.model.WalletEntity;
import com.kunlanw.design.service.IWalletService;
import com.kunlanw.design.until.Constant;
import com.kunlanw.design.until.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/wallet")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    /**
     * 保存钱包地址
     * @param wallet
     * @return
     */
    @RequestMapping(value = "/createWallet",method = RequestMethod.POST)
    public ResponseResult createWallet(@RequestBody Wallet wallet){
        ResponseResult  result=new ResponseResult();
        result.setCode(0);
        try{
            boolean res=this.walletService.createWallet(wallet);
            result.setResult(res);
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取用户钱包地址
     * @return
     */
    @RequestMapping(value = "/getWalletByUserId",method = RequestMethod.GET)
    public ResponseResult getWalletByUserId(HttpSession session){
        ResponseResult  result=new ResponseResult();
        result.setCode(0);
        try{
            int id=(Integer) session.getAttribute(Constant.User_Session);
            List<WalletEntity> wallets=this.walletService.getWalletByUserId(id);
            if(wallets!=null&&!wallets.isEmpty()){
                result.setResult(wallets);
            }
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;

    }

    /**
     * 删除钱包
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteWallet/{id}",method = RequestMethod.DELETE)
    public ResponseResult deleteById(@PathVariable int id){
        ResponseResult  result=new ResponseResult();
        result.setCode(0);
        try{
            boolean res=this.walletService.deleteWalletById(id);
            result.setResult(res);
            result.setMessage("successful");
        }catch (Exception e){
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
