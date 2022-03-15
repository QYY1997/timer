package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/8  17:33
 * @desc :投稿信息
 */
public class StatVideoModel {
    /**
     * aid : 544393890
     * view : 791254
     * danmaku : 1875
     * reply : 2710
     * favorite : 16105
     * coin : 48066
     * share : 1803
     * now_rank : 0
     * his_rank : 43
     * like : 90448
     * dislike : 0
     * evaluation :
     * argue_msg :
     */

    private String aid;
    private int view;
    private int danmaku;
    private int reply;
    private int favorite;
    private int coin;
    private int share;
    private int now_rank;
    private int his_rank;
    private int like;
    private int dislike;
    private String evaluation;
    private String argue_msg;

    public String getAid() {
        return aid == null ? "" : aid;
    }

    public void setAid(String aid) {
        this.aid = (aid == null ? "" : aid);
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(int danmaku) {
        this.danmaku = danmaku;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getNow_rank() {
        return now_rank;
    }

    public void setNow_rank(int now_rank) {
        this.now_rank = now_rank;
    }

    public int getHis_rank() {
        return his_rank;
    }

    public void setHis_rank(int his_rank) {
        this.his_rank = his_rank;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getEvaluation() {
        return evaluation == null ? "" : evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = (evaluation == null ? "" : evaluation);
    }

    public String getArgue_msg() {
        return argue_msg == null ? "" : argue_msg;
    }

    public void setArgue_msg(String argue_msg) {
        this.argue_msg = (argue_msg == null ? "" : argue_msg);
    }
}
