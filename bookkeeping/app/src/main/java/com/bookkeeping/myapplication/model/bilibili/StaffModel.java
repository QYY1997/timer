package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:30
 * @desc :
 */
public class StaffModel {
    /**
     * mid : 808171
     * title : UP主
     * name : 吃素的狮子
     * face : http://i1.hdslb.com/bfs/face/249bfa1b3d3e0932f533bc5364964b132fe9c6c2.jpg
     * vip : {"type":2,"status":1,"due_date":1633190400000,"vip_pay_type":0,"theme_type":0,"label":{"path":"","text":"年度大会员","label_theme":"annual_vip","text_color":"#FFFFFF","bg_style":1,"bg_color":"#FB7299","border_color":""},"avatar_subscript":1,"nickname_color":"#FB7299","role":3,"avatar_subscript_url":"http://i0.hdslb.com/bfs/vip/icon_Certification_big_member_22_3x.png"}
     * official : {"role":1,"title":"bilibili 2018百大UP主、 知名UP主","desc":"","type":0}
     * follower : 3360509
     * label_style : 0
     */

    private int mid;
    private String title;
    private String name;
    private String face;
    private VipModel vip;
    private OfficialVerifyModel official;
    private int follower;
    private int label_style;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = face == null ? "" : face;
    }

    public VipModel getVip() {
        return vip==null?new VipModel() :vip;
    }

    public void setVip(VipModel vip) {
        this.vip = vip;
    }

    public OfficialVerifyModel getOfficial() {
        return official;
    }

    public void setOfficial(OfficialVerifyModel official) {
        this.official = official;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getLabel_style() {
        return label_style;
    }

    public void setLabel_style(int label_style) {
        this.label_style = label_style;
    }

}
