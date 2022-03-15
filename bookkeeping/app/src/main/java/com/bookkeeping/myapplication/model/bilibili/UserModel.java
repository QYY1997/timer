package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  15:03
 * @desc :
 */
public class UserModel {
    /**
     * uid : 3306866
     * uname : 养乌龙猫的欠欠
     * face : https://i2.hdslb.com/bfs/face/2cd5ad6ab0c61eeba3fd250725c7f14c4ff8d435.jpg
     */

    private String uid;
    private String uname;
    private String face;

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = (uid == null ? "" : uid);
    }

    public String getUname() {
        return uname == null ? "" : uname;
    }

    public void setUname(String uname) {
        this.uname = (uname == null ? "" : uname);
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = (face == null ? "" : face);
    }
}
