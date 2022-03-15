package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/8  17:25
 * @desc :投稿视频动态 type:8
 */
public class DynamicVideoModel  {

    /**
     * aid : 589085137
     * attribute : 0
     * cid : 366206138
     * copyright : 1
     * ctime : 1625717495
     * desc : 想不到吧这稿我能从520屯到现在
     * dimension : {}
     * duration : 294
     * dynamic : 我的闺蜜总是带我去一些难以描述的场所
     * item : {}
     * jump_url : bilibili://video/589085137/?page=1&player_preload=null&player_width=1920&player_height=1080&player_rotate=0
     * mission_id : 22558
     * owner : {}
     * pic : https://i2.hdslb.com/bfs/archive/fafd0e90ffcee06a432d74f017419a51222b9026.jpg
     * player_info : null
     * pubdate : 1625731204
     * rights : {}
     * short_link : https://b23.tv/BV1pB4y1N7m5
     * short_link_v2 : https://b23.tv/BV1pB4y1N7m5
     * stat : {}
     * state : 0
     * tid : 21
     * title : 舞蹈区up保持好身材的秘密！~都在这里了
     * tname : 日常
     * videos : 1
     */

    private String aid;
    private String attribute;
    private String cid;
    private String copyright;
    private long ctime;
    private String desc;
    private DimensionModel dimension;
    private int duration;
    private String dynamic;
    private ItemPicturesModel item;
    private String jump_url;
    private int mission_id;
    private OwnerModel owner;
    private String pic;
    private String share_subtitle;
    private Object player_info;
    private long pubdate;
    private RightsModel rights;
    private String short_link;
    private String short_link_v2;
    private StatVideoModel stat;
    private int state;
    private int tid;
    private String title;
    private String tname;
    private int videos;

    public String getAid() {
        return aid == null ? "" : aid;
    }

    public void setAid(String aid) {
        this.aid = (aid == null ? "" : aid);
    }

    public String getAttribute() {
        return attribute == null ? "" : attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = (attribute == null ? "" : attribute);
    }

    public String getCid() {
        return cid == null ? "" : cid;
    }

    public void setCid(String cid) {
        this.cid = (cid == null ? "" : cid);
    }

    public String getCopyright() {
        return copyright == null ? "" : copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = (copyright == null ? "" : copyright);
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getShare_subtitle() {
        return share_subtitle == null ? "" : share_subtitle;
    }

    public void setShare_subtitle(String share_subtitle) {
        this.share_subtitle = (share_subtitle == null ? "" : share_subtitle);
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = (desc == null ? "" : desc);
    }

    public DimensionModel getDimension() {
        return dimension;
    }

    public void setDimension(DimensionModel dimension) {
        this.dimension = dimension;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDynamic() {
        return dynamic == null ? "" : dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = (dynamic == null ? "" : dynamic);
    }

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public String getJump_url() {
        return jump_url == null ? "" : jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = (jump_url == null ? "" : jump_url);
    }

    public int getMission_id() {
        return mission_id;
    }

    public void setMission_id(int mission_id) {
        this.mission_id = mission_id;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public String getPic() {
        return pic == null ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = (pic == null ? "" : pic);
    }

    public Object getPlayer_info() {
        return player_info;
    }

    public void setPlayer_info(Object player_info) {
        this.player_info = player_info;
    }

    public long getPubdate() {
        return pubdate;
    }

    public void setPubdate(long pubdate) {
        this.pubdate = pubdate;
    }

    public RightsModel getRights() {
        return rights;
    }

    public void setRights(RightsModel rights) {
        this.rights = rights;
    }

    public String getShort_link() {
        return short_link == null ? "" : short_link;
    }

    public void setShort_link(String short_link) {
        this.short_link = (short_link == null ? "" : short_link);
    }

    public String getShort_link_v2() {
        return short_link_v2 == null ? "" : short_link_v2;
    }

    public void setShort_link_v2(String short_link_v2) {
        this.short_link_v2 = (short_link_v2 == null ? "" : short_link_v2);
    }

    public StatVideoModel getStat() {
        return stat;
    }

    public void setStat(StatVideoModel stat) {
        this.stat = stat;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public String getTname() {
        return tname == null ? "" : tname;
    }

    public void setTname(String tname) {
        this.tname = (tname == null ? "" : tname);
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }
}
