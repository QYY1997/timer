package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/12  17:45
 * @desc :
 */
public class ShowTipModel {
    /**
     * del_tip : 要删除动态吗？
     */

    private String del_tip;

    public String getDel_tip() {
        return del_tip == null ? "" : del_tip;
    }

    public void setDel_tip(String del_tip) {
        this.del_tip = (del_tip == null ? "" : del_tip);
    }
}
