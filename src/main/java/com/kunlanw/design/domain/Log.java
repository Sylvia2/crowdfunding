package com.kunlanw.design.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Log implements Serializable {
    private Integer logid;

    private Integer userid;

    private Integer projectid;

    private Date datachangeCreatetime;

    private Date datachangeLasttime;

    private Long amount;

    private Integer walletid;

    public Integer getWalletid() {
        return walletid;
    }

    public void setWalletid(Integer walletid) {
        this.walletid = walletid;
    }

    private static final long serialVersionUID = 1L;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Date getDatachangeCreatetime() {
        return datachangeCreatetime;
    }

    public void setDatachangeCreatetime(Date datachangeCreatetime) {
        this.datachangeCreatetime = datachangeCreatetime;
    }

    public Date getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Date datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}