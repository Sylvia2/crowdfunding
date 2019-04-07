package com.kunlanw.design.contract;


import com.kunlanw.design.model.ContractProject;
import com.kunlanw.design.model.WalletEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数据上链，和只能合约交互
 */
public interface IFundService {

    boolean createProject(BigInteger amount, String address_to);

    boolean fund(String address_to,String address_from,String key,BigInteger amount);

    ContractProject getProjectOnContract(String address) throws Exception;

    BigDecimal getWalletAmount(String address)throws Exception;

}
