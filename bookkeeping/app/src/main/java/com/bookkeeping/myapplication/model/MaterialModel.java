package com.bookkeeping.myapplication.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : qiuyiyang
 * @date : 2021/9/27  15:39
 * @desc :
 */
public class MaterialModel implements Serializable ,Cloneable{
    private String id;
    //物品ID
    private String name;
    //物品名称
    private String type;
    //物品类型
    private int sellPrice;
    //售价
    private int vitality;
    //活力
    private int price;
    //价格、进价、计算成本
    private int number;
    //数量
    private int isSynthesis;
    //是否为合成
    private BigDecimal profit;
    //计算利润
    private BigDecimal costPerformance;
    //计算性价比
    private String  parentID;
    private String  childId;

    public String getChildId() {
        return childId == null ? "" : childId;
    }

    public void setChildId(String childId) {
        this.childId = (childId == null ? "" : childId);
    }

    public String getParentID() {
        return parentID == null ? "" : parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = (parentID == null ? "" : parentID);
    }

    public int getIsSynthesis() {
        return isSynthesis;
    }

    public void setIsSynthesis(int isSynthesis) {
        this.isSynthesis = isSynthesis;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getProfit() {
        return profit==null?new BigDecimal("0"):profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getCostPerformance() {
        return costPerformance==null?new BigDecimal("0"):costPerformance;
    }

    public void setCostPerformance(BigDecimal costPerformance) {
        this.costPerformance = costPerformance;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = (name == null ? "" : name);
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString (){
       return "id="+getId()+",name="+getName()+",type="+getType()+",sellPrice="+getSellPrice()+",vitality="+getVitality()+",price="+getPrice()+
                ",number="+getNumber()+",isSynthesis="+getIsSynthesis()+",profit="+getPrice()+",costPerformance="+getCostPerformance()+",parentID="+getParentID();
    }

    @Override
    public MaterialModel clone() throws CloneNotSupportedException {
        MaterialModel materialModel = new MaterialModel();
        try {
            materialModel = (MaterialModel) super.clone();
            return materialModel;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return materialModel;
    }
}


