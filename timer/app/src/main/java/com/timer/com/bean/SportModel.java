package com.timer.com.bean;

/**
 * @author : qiuyiyang
 * @date : 2022/2/12  2:30
 * @desc :
 */
public class SportModel {

    /**
     * cls : 二年级
     * enRolNum : 0
     * grade : 小学
     * groupNum : 7
     * id : 5cd12b915b1561009631adf4
     * roadNum : 8
     * rule : 3
     * school : b
     * state : 3
     * title : 100米
     * type : 田径运动
     * validityBegin : 1557212040000
     * validityEnd : 1557212040000
     */

    private String cls;
    private int enRolNum;
    private String grade;
    private int groupNum;
    private String id;
    private int roadNum;
    private String rule;
    private String school;
    private int state;
    private String title;
    private String type;
    private long validityBegin;
    private long validityEnd;

    public String getCls() {
        return cls == null ? "" : cls;
    }

    public void setCls(String cls) {
        this.cls = (cls == null ? "" : cls);
    }

    public int getEnRolNum() {
        return enRolNum;
    }

    public void setEnRolNum(int enRolNum) {
        this.enRolNum = enRolNum;
    }

    public String getGrade() {
        return grade == null ? "" : grade;
    }

    public void setGrade(String grade) {
        this.grade = (grade == null ? "" : grade);
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public int getRoadNum() {
        return roadNum;
    }

    public void setRoadNum(int roadNum) {
        this.roadNum = roadNum;
    }

    public String getRule() {
        return rule == null ? "" : rule;
    }

    public void setRule(String rule) {
        this.rule = (rule == null ? "" : rule);
    }

    public String getSchool() {
        return school == null ? "" : school;
    }

    public void setSchool(String school) {
        this.school = (school == null ? "" : school);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public long getValidityBegin() {
        return validityBegin;
    }

    public void setValidityBegin(long validityBegin) {
        this.validityBegin = validityBegin;
    }

    public long getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(long validityEnd) {
        this.validityEnd = validityEnd;
    }
}
