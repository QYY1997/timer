package com.bookkeeping.myapplication.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : qiuyiyang
 * date   : 2021/1/4  17:41
 * desc   :
 */
public class BookModel implements Serializable {
    private String id;
    private long useTime;
    private long creatTime;
    private long updateTime;
    private String type;
    private String remark;
    private BigDecimal trxMoney;
    private String event;
    private String balance;

    public String getBalance() {
        return balance == null ? "" : balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTrxMoney() {
        return trxMoney;
    }

    public void setTrxMoney(BigDecimal trxMoney) {
        this.trxMoney = trxMoney;
    }

    public String getEvent() {
        return event == null ? "" : event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
