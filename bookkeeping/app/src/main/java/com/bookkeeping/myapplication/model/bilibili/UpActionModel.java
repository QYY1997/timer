package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/3/29  11:04
 * @desc :
 */
public class UpActionModel {

    /**
     * like : false
     * reply : false
     */

    private boolean like;
    //是否点赞
    private boolean reply;
    //是否回复

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }
}
