package com.kunlanw.design.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "log")
@Getter
@Setter
public class Log implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int logID;
    private int userID;
    private int projectID;
    private BigDecimal amount;
    private Date datachange_createtime;
    private Date datachange_lasttime;

}
