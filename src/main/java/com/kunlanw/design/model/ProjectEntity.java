package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectEntity {
    private int projectID;
    private String projectName;
    private BigDecimal projectAmount;
    private int walletID;
    private int userID;
    private String desc;
    private Date deadline;
    private String type;
    private String status;
    private Date datachange_createtime;
    private Date datachange_lasttime;
}
