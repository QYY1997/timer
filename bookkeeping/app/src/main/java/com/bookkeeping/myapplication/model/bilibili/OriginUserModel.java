package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  16:18
 * @desc :
 */
public class OriginUserModel {
    /**
     * info : {"uid":233114659,"uname":"碧蓝航线","face":"https://i1.hdslb.com/bfs/face/6392ff1bcdddeba4429aed0f67125b2fa402936d.jpg"}
     * card : {"official_verify":{"type":1,"desc":"碧蓝航线官方账号"}}
     * vip : {"vipType":2,"vipDueDate":1631203200000,"vipStatus":1,"themeType":0,"label":{"path":"","text":"年度大会员","label_theme":"annual_vip","text_color":"#FFFFFF","bg_style":1,"bg_color":"#FB7299","border_color":""},"avatar_subscript":1,"nickname_color":"#FB7299","role":3,"avatar_subscript_url":"https://i0.hdslb.com/bfs/vip/icon_Certification_big_member_22_3x.png"}
     * pendant : {"pid":1987,"name":"碧蓝航线2020","image":"https://i1.hdslb.com/bfs/garb/item/fe1267f786bf69f1471aff715f8d38ec0e486df5.png","expire":0,"image_enhance":"https://i1.hdslb.com/bfs/garb/item/0aa9fd33133ed3fd9f11c857cc6ca848d6804113.webp","image_enhance_frame":"https://i1.hdslb.com/bfs/garb/item/3052b412defbbc7704e887fefde8de539e8027c5.png"}
     * rank : 10000
     * sign : 今天有没有被指挥官偷瞄呢(:3_ヽ)_
     * level_info : {"current_level":6}
     */

    private UserModel info;
    private OfficialVerifyModel card;
    private VipPersonModel vip;
    private PendantModel pendant;
    private String rank;
    private String sign;
    private LevelModel level_info;

    public UserModel getInfo() {
        return info!=null?info:new UserModel();
    }

    public void setInfo(UserModel info) {
        this.info = info;
    }

    public OfficialVerifyModel getCard() {
        return card!=null?card:new OfficialVerifyModel();
    }

    public void setCard(OfficialVerifyModel card) {
        this.card = card;
    }

    public VipPersonModel getVip() {
        return vip!=null?vip:new VipPersonModel();
    }

    public void setVip(VipPersonModel vip) {
        this.vip = vip;
    }

    public PendantModel getPendant() {
        return pendant!=null?pendant:new PendantModel();
    }

    public void setPendant(PendantModel pendant) {
        this.pendant = pendant;
    }

    public String getRank() {
        return rank == null ? "" : rank;
    }

    public void setRank(String rank) {
        this.rank = (rank == null ? "" : rank);
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = (sign == null ? "" : sign);
    }

    public LevelModel getLevel_info() {
        return level_info!=null?level_info:new LevelModel();
    }

    public void setLevel_info(LevelModel level_info) {
        this.level_info = level_info;
    }
}
