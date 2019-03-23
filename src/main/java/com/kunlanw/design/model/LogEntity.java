package com.kunlanw.design.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LogEntity {

    private int logId;
    private int fromAddress;
    private String projectName;
    private int projectId;
    private long amount;
    private String date;
}
