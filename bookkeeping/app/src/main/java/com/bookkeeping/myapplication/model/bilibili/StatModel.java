package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/6/10  16:52
 * @desc :个人信息
 */
public class StatModel {

    /**
     * mid : 808171
     * following : 223
     * whisper : 0
     * black : 0
     * follower : 3362862
     */

    private String mid;
    private int following;
    private int whisper;
    private int black;
    private int follower;

    public String getMid() {
        return mid == null ? "" : mid;
    }

    public void setMid(String mid) {
        this.mid = (mid == null ? "" : mid);
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getWhisper() {
        return whisper;
    }

    public void setWhisper(int whisper) {
        this.whisper = whisper;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
