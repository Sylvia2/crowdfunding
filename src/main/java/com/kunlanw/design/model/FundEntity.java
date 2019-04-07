package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class FundEntity {
    private int projectId;
    private String address_to;
    private int toID;
    private int fromID;
    private String address_from;
    private BigDecimal amount;
    private int userID;
    private String key;
}
