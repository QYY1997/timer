package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/3/29  11:03
 * @desc :
 */
public class FolderModel {

    /**
     * has_folded : false
     * is_folded : false
     * rule : https://www.bilibili.com/blackboard/foldingreply.html
     */

    private boolean has_folded;
    private boolean is_folded;
    private String rule;

    public boolean isHas_folded() {
        return has_folded;
    }

    public void setHas_folded(boolean has_folded) {
        this.has_folded = has_folded;
    }

    public boolean isIs_folded() {
        return is_folded;
    }

    public void setIs_folded(boolean is_folded) {
        this.is_folded = is_folded;
    }

    public String getRule() {
        return rule == null ? "" : rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
