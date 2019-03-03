package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FundEntity {
    private int projectId;
    private String address_to;
    private String address_from;
    private BigInteger amount;
    private int userID;
}
