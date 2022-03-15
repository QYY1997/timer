package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  16:41
 * @desc :
 */
public class MemberModel {

    /**
     * mid : 285499073
     * uname : 东尼ookii
     * sex : 男
     * sign : 日常发福利在微博: 东尼ookii，客官！进来就关注一下嘛～
     * avatar : http://i2.hdslb.com/bfs/face/89975e4a5de742d5cd84c04f21a7402e6d9f3a38.jpg
     * rank : 10000
     * DisplayRank : 0
     * level_info : {"current_level":6,"current_min":0,"current_exp":0,"next_exp":0}
     * pendant : {"pid":3676,"name":"战双帕弥什","image":"http://i2.hdslb.com/bfs/garb/item/85545abf17d26ff0d3d00ef35ccefb03aaadd50f.png","expire":0,"image_enhance":"http://i2.hdslb.com/bfs/garb/item/ef9213abeb3e22ff187545fb067a997fdf309f1b.webp","image_enhance_frame":"http://i2.hdslb.com/bfs/garb/item/1570dbd6469fb5af2de3b36a6bf33cd23d1680bd.png"}
     * nameplate : {"nid":1,"name":"黄金殿堂","image":"http://i0.hdslb.com/bfs/face/82896ff40fcb4e7c7259cb98056975830cb55695.png","image_small":"http://i1.hdslb.com/bfs/face/627e342851dfda6fe7380c2fa0cbd7fae2e61533.png","level":"稀有勋章","condition":"单个自制视频总播放数>=100万"}
     * official_verify : {"type":0,"desc":"bilibili 2020百大UP主、知名UP主"}
     * vip : {"vipType":2,"vipDueDate":1629216000000,"dueRemark":"","accessStatus":0,"vipStatus":1,"vipStatusWarn":"","themeType":0,"label":{"path":"","text":"年度大会员","label_theme":"annual_vip","text_color":"#FFFFFF","bg_style":1,"bg_color":"#FB7299","border_color":""},"avatar_subscript":1,"nickname_color":"#FB7299"}
     * fans_detail : null
     * following : 0
     * is_followed : 0
     * user_sailing : {"pendant":null,"cardbg":null,"cardbg_with_focus":null}
     * is_contractor : false
     */

    private String mid;//uid
    private String uname;
    private String sex;//性别：男、女、保密
    private String sign;//签名
    private String avatar;//头像地址
    private String rank;
    private String DisplayRank;
    private LevelModel level_info;//等级信息
    private PendantModel pendant;//头像框
    private NameplateModel nameplate; //勋章
    private OfficialVerifyModel official_verify;
    private VipPersonModel vip;
    private FansDetailModel fans_detail;
    private int following;
    private int is_followed;
    private UserSailingModel user_sailing;
    private boolean is_contractor;

    public String getMid() {
        return mid == null ? "" : mid;
    }

    public void setMid(String mid) {
        this.mid = (mid == null ? "" : mid);
    }

    public String getUname() {
        return uname == null ? "" : uname;
    }

    public void setUname(String uname) {
        this.uname = (uname == null ? "" : uname);
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = (sex == null ? "" : sex);
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = (sign == null ? "" : sign);
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = (avatar == null ? "" : avatar);
    }

    public String getRank() {
        return rank == null ? "" : rank;
    }

    public void setRank(String rank) {
        this.rank = (rank == null ? "" : rank);
    }

    public String getDisplayRank() {
        return DisplayRank == null ? "" : DisplayRank;
    }

    public void setDisplayRank(String displayRank) {
        DisplayRank = (displayRank == null ? "" : displayRank);
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

    public OfficialVerifyModel getOfficial_verify() {
        return official_verify;
    }

    public void setOfficial_verify(OfficialVerifyModel official_verify) {
        this.official_verify = official_verify;
    }

    public VipPersonModel getVip() {
        return vip;
    }

    public void setVip(VipPersonModel vip) {
        this.vip = vip;
    }

    public FansDetailModel getFans_detail() {
        return fans_detail;
    }

    public void setFans_detail(FansDetailModel fans_detail) {
        this.fans_detail = fans_detail;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
    }

    public UserSailingModel getUser_sailing() {
        return user_sailing;
    }

    public void setUser_sailing(UserSailingModel user_sailing) {
        this.user_sailing = user_sailing;
    }

    public boolean isIs_contractor() {
        return is_contractor;
    }

    public void setIs_contractor(boolean is_contractor) {
        this.is_contractor = is_contractor;
    }
}
