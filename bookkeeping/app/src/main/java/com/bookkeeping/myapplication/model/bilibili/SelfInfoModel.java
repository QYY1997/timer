package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/26  16:24
 * @desc :
 */
public class SelfInfoModel {

    /**
     * mid : 24526478
     * name : 橙色夕阳_Q
     * approve : false
     * sex : 男
     * rank : 10000
     * face : http://i2.hdslb.com/bfs/face/fcea7cdd8c190ec1080d95cca95d4397c38f1d0c.jpg
     * DisplayRank : 0
     * regtime : 0
     * spacesta : 0
     * birthday :
     * place :
     * description :
     * article : 0
     * attentions : []
     * fans : 0
     * friend : 87
     * attention : 87
     * sign : 敢问路在何方，路在脚下！
     * level_info : {}
     * pendant : {}
     * nameplate : {}
     * Official : {}
     * official_verify : {}
     * vip : {}
     */

    private String mid;
    private String name;
    private boolean approve;
    private String sex;
    private String rank;
    private String face;
    private String DisplayRank;
    private long regtime;
    private int spacesta;
    private String birthday;
    private String place;
    private String description;
    private int article;
    private int fans;
    private int friend;
    private int attention;
    private String sign;
    private LevelModel level_info;
    private PendantModel pendant;
    private NameplateModel nameplate;
    private OfficialVerifyModel Official;
    private OfficialVerifyModel official_verify;
    private VipModel vip;
    private List<AttentionsModel> attentions;

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

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = (sex == null ? "" : sex);
    }

    public String getRank() {
        return rank == null ? "" : rank;
    }

    public void setRank(String rank) {
        this.rank = (rank == null ? "" : rank);
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = (face == null ? "" : face);
    }

    public String getDisplayRank() {
        return DisplayRank == null ? "" : DisplayRank;
    }

    public void setDisplayRank(String displayRank) {
        DisplayRank = (displayRank == null ? "" : displayRank);
    }

    public long getRegtime() {
        return regtime;
    }

    public void setRegtime(long regtime) {
        this.regtime = regtime;
    }

    public int getSpacesta() {
        return spacesta;
    }

    public void setSpacesta(int spacesta) {
        this.spacesta = spacesta;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = (birthday == null ? "" : birthday);
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public void setPlace(String place) {
        this.place = (place == null ? "" : place);
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = (description == null ? "" : description);
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = (sign == null ? "" : sign);
    }

    public LevelModel getLevel_info() {
        return level_info;
    }

    public void setLevel_info(LevelModel level_info) {
        this.level_info = level_info;
    }

    public PendantModel getPendant() {
        return pendant;
    }

    public void setPendant(PendantModel pendant) {
        this.pendant = pendant;
    }

    public NameplateModel getNameplate() {
        return nameplate;
    }

    public void setNameplate(NameplateModel nameplate) {
        this.nameplate = nameplate;
    }

    public OfficialVerifyModel getOfficial() {
        return Official;
    }

    public void setOfficial(OfficialVerifyModel official) {
        Official = official;
    }

    public OfficialVerifyModel getOfficial_verify() {
        return official_verify;
    }

    public void setOfficial_verify(OfficialVerifyModel official_verify) {
        this.official_verify = official_verify;
    }

    public VipModel getVip() {
        return vip;
    }

    public void setVip(VipModel vip) {
        this.vip = vip;
    }

    public List<AttentionsModel> getAttentions() {
        if (attentions == null) {
            return new ArrayList<>();
        }
        return attentions;
    }

    public void setAttentions(List<AttentionsModel> attentions) {
        this.attentions = attentions;
    }
}
