package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectEntity {
    private int projectid;

    private String projectname;

    private BigDecimal projectamount;

    private int walletid;

    private String walletAddress;

    private int userid;

    private String username;

    private String desc;

    private Date deadline;

    private int type;

    private short status;

    private String dateName;

    private String dataCreateTime;



}
