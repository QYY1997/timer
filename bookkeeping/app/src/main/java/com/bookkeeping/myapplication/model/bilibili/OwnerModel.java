package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/8  17:29
 * @desc :
 */
public class OwnerModel {
    /**
     * mid : 808171
     * name : 吃素的狮子
     * face : http://i1.hdslb.com/bfs/face/249bfa1b3d3e0932f533bc5364964b132fe9c6c2.jpg
     */

    private String mid;
    private String name;
    private String face;

    public String getMid() {
        return mid == null ? "" : mid;
    }

    public void setMid(String mid) {
        this.mid = (mid == null ? "" : mid);
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = (name == null ? "" : name);
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = (face == null ? "" : face);
    }
}
