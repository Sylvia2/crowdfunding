package com.kunlanw.design.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    private String username;
    private String userpassword;
    private String useremail;
    private short sex;
    private Date  datachange_createtime;
    private Date datachange_lastTime;

}
