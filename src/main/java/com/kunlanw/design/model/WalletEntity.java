package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletEntity {
    private int id;
    private String address;
    private BigDecimal amount;
}
