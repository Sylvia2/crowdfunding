package com.kunlanw.design.contract;


import com.kunlanw.design.model.ContractProject;

import com.kunlanw.design.until.Constant;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.core.DefaultBlockParameterName;

import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Strings;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 与智能合约交互
 */
@Service
public class FundService implements IFundService {

    /**
     * 加载合约
     *
     * @return
     */
    private Fund loadFund() {
        Web3j web3j = Web3j.build(new HttpService());
        Credentials credentials = Credentials.create(Constant.Private_Key);
        Fund fund = Fund.load(Constant.Contract_Address, web3j, credentials, Constant.GAS_PRICE, Constant.GAS_LIMIT);
        return fund;
    }

    /**
     * 创建项目
     *
     * @param
     * @return
     */
    @Override
    public boolean createProject(BigInteger amount, String address_to) {
        try {
            Fund fund = this.loadFund();
            //与智能合约交互，创建项目
            TransactionReceipt receipt = fund.createProject(amount, address_to, BigInteger.ZERO).send();
            if (receipt.isStatusOK() && !receipt.getTransactionHash().isEmpty()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 众筹过程
     *
     * @param address_to
     * @param address_from
     * @param amount
     * @return
     */
    @Override
    public boolean fund(String address_to, String address_from, String key, BigInteger amount) {
        try {
            Web3j web3j = Web3j.build(new HttpService());
            if (this.transfer(address_to, key, amount)) {
                Credentials credentials = Credentials.create(Constant.Private_Key);
                Fund fund = Fund.load(Constant.Contract_Address, web3j, credentials, Constant.GAS_PRICE, Constant.GAS_LIMIT);
                TransactionReceipt receipt = fund.fund(amount, address_to, address_from, BigInteger.ZERO).send();
                if (receipt.getTransactionHash() != null && receipt.isStatusOK()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 两个账户之间的转账
     *
     * @param address_to
     * @param key
     * @param amount
     * @return
     */
    private boolean transfer(String address_to, String key, BigInteger amount) {
        try {
            Web3j web3j = Web3j.build(new HttpService());
            Credentials credentials = Credentials.create(key);
            TransactionReceipt receipt = Transfer.sendFunds(web3j, credentials, address_to, new BigDecimal(amount), Convert.Unit.ETHER).send();
            if (receipt.isStatusOK() && !Strings.isEmpty(receipt.getTransactionHash())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 获取实时数据
     *
     * @param address
     * @return
     */
    @Override
    public ContractProject getProjectOnContract(String address) throws Exception {
        try {
            Fund fund = this.loadFund();
            ContractProject project = new ContractProject();
            project.setAddress(address);
            BigInteger realTimeAmount = fund.realTimeAmount(address).send();
            BigInteger successAmount = fund.successAmount(address).send();
            BigInteger paidNum = fund.paidNum(address).send();
            BigInteger status = fund.status(address).send();
            project.setRealtimeAmount(realTimeAmount);
            project.setPaidNum(paidNum);
            project.setSuccessAmount(successAmount);
            project.setStatus(status);
            return project;
        } catch (Exception e) {
            throw new Exception("获取项目实时金额失败");
        }
    }

    @Override
    public BigDecimal getWalletAmount(String address) throws Exception {
        Web3j web3j = Web3j.build(new HttpService());
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigDecimal amount = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER);
        return amount;
    }


}
