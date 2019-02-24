package com.kunlanw.design.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectID;
    private String projectName;
    private BigDecimal projectAmount;
    private int walletID;
    private int userID;
    private String desc;
    private Date deadline;
    private int type;
    private short status;
    private Date datachange_createtime;
    private Date datachange_lasttime;



}
