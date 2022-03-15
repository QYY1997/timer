package com.bookkeeping.myapplication.model;

/**
 * @author : qiuyiyang
 * @date : 2021/9/27  17:09
 * @desc :
 */
public class SynthesisModel {
    private String id;
    private String childID;
    private String parentID;
    private int number;
    private int isSynthesis;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getChildID() {
        return childID == null ? "" : childID;
    }

    public void setChildID(String childID) {
        this.childID = (childID == null ? "" : childID);
    }

    public String getParentID() {
        return parentID == null ? "" : parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = (parentID == null ? "" : parentID);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIsSynthesis() {
        return isSynthesis;
    }

    public void setIsSynthesis(int isSynthesis) {
        this.isSynthesis = isSynthesis;
    }
}
