package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectEntity {
    private Integer projectid;

    private String projectname;

    private BigDecimal projectamount;

    private Integer walletid;

    private String walletAddress;

    private Integer userid;

    private String username;

    private String desc;

    private Date deadline;

    private Integer type;

    private Short status;



}
