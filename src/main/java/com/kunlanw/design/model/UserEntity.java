package com.kunlanw.design.model;

import com.kunlanw.design.domain.Wallet;
import lombok.Data;

import java.util.List;

@Data
public class UserEntity {
    private int userid;
    private String name;
    private String email;
    private Byte sex;
    private List<Wallet> wallets;
}
