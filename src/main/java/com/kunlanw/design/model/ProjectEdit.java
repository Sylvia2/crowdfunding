package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectEdit {
    private String name;
    private String desc;
    private String useremail;
    private int walletAddress;
    private BigDecimal amount;
    private int type;
    private Date deadline;
}
