package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:22
 * @desc :
 */
public class NameplateModel {
    /**
     * nid : 1
     * name : 黄金殿堂
     * image : http://i0.hdslb.com/bfs/face/82896ff40fcb4e7c7259cb98056975830cb55695.png
     * image_small : http://i1.hdslb.com/bfs/face/627e342851dfda6fe7380c2fa0cbd7fae2e61533.png
     * level : 稀有勋章
     * condition : 单个自制视频总播放数>=100万
     */

    private int nid;//勋章id
    private String name;//勋章名
    private String image; //勋章地址
    private String image_small;//勋章地址（小）
    private String level;//勋章类型
    private String condition;//勋章说明

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_small() {
        return image_small == null ? "" : image_small;
    }

    public void setImage_small(String image_small) {
        this.image_small = image_small;
    }

    public String getLevel() {
        return level == null ? "" : level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCondition() {
        return condition == null ? "" : condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}