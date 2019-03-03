package com.kunlanw.design.contract;


import com.kunlanw.design.model.ContractProject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数据上链，和只能合约交互
 */
public interface IFundService {

    boolean createProject(BigInteger amount, String address_to);

    boolean fund(String address_to,String address_from,BigInteger amount);

    ContractProject getProjectOnContract(String address) throws Exception;

}
