package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ContractProject {
    /**
     * 实时金额
     */
    private BigInteger realtimeAmount;
    /**
     * 该项目上的投资总次数
     */
    private BigInteger paidNum;
    /**
     * 该项目的目标金额
     */
    private BigInteger successAmount;
    /**
     * 该项目的状态(0,1)
     */
    private BigInteger status;
    /**
     * 项目的钱包地址
     */
    private String address;

}
