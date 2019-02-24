package com.kunlanw.design.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "wallet")
@Getter
@Setter
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int walletID;
    private int userID;
    private String address;
    private Date  datachange_createtime;
    private Date datachange_lasttime;

}
