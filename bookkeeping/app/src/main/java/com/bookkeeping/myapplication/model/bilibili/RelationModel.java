package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/12  16:57
 * @desc :
 */
public class RelationModel {
    /**
     * status : 2
     * is_follow : 1
     * is_followed : 0
     */

    private int status;
    private int is_follow;
    private int is_followed;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
    }
}
