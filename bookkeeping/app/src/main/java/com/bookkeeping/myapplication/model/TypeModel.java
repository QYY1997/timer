package com.bookkeeping.myapplication.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : qiuyiyang
 * @date : 2021/1/14  15:11
 * @desc :
 */
public class TypeModel  implements Serializable {

    private String id;
    private BigDecimal balance;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
