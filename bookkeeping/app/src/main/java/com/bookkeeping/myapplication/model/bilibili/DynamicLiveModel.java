package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  17:02
 * @desc : 直播动态
 */
public class DynamicLiveModel {

    /**
     * area : 6
     * area_v2_id : 399
     * area_v2_name : 日常
     * area_v2_parent_id : 1
     * area_v2_parent_name : 娱乐
     * attentions : 0
     * background : https:\/\/i0.hdslb.com\/bfs\/live\/2388faed3728f3396052273ad4c3c9af21c411fc.jpg
     * broadcast_type : 0
     * cover : https:\/\/i0.hdslb.com\/bfs\/live\/new_room_cover\/3d46d87d53bfb0ae58f6d516709ab1d8711dcac6.jpg
     * face : https:\/\/i0.hdslb.com\/bfs\/face\/8fa0a5cacd03b0062ffafd073afaecfe30811d4b.jpg
     * first_live_time : 1476009378
     * hidden_status : 0000-00-00 00:00:00
     * item : {}
     * link : &
     * live_id : 152710499967051226
     * live_status : 1
     * live_time : 2021-07-16 14:59:58
     * lock_status : 0000-00-00 00:00:00
     * on_flag : 0
     * online : 3350
     * room_shield : 0
     * room_silent : 0
     * roomid : 140762
     * round_status : 1
     * short_id : 0
     * slide_link : https:\/\/live.bilibili.com\/140762?broadcast_type=0&is_room_feed=1
     * tags : 板绘
     * title : 画舰长礼物~
     * try_time : 0000-00-00 00:00:00
     * uid : 449562
     * uname : 萌玘
     * user_cover : https:\/\/i0.hdslb.com\/bfs\/live\/new_room_cover\/3d46d87d53bfb0ae58f6d516709ab1d8711dcac6.jpg
     * verify :
     * virtual : 1
     */

    private int area;
    private String area_v2_id;
    private String area_v2_name;
    private int area_v2_parent_id;
    private String area_v2_parent_name;
    private int attentions;
    private String background;
    private int broadcast_type;
    private String cover;
    private String face;
    private long first_live_time;
    private String hidden_status;
    private ItemPicturesModel item;
    private String link;
    private String live_id;
    private int live_status;
    private String live_time;
    private String lock_status;
    private int on_flag;
    private int online;
    private int room_shield;
    private int room_silent;
    private int roomid;
    private int round_status;
    private String short_id;
    private String slide_link;
    private String tags;
    private String title;
    private String try_time;
    private String uid;
    private String uname;
    private String user_cover;
    private String verify;
    private int virtual;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getArea_v2_id() {
        return area_v2_id == null ? "" : area_v2_id;
    }

    public void setArea_v2_id(String area_v2_id) {
        this.area_v2_id = (area_v2_id == null ? "" : area_v2_id);
    }

    public String getArea_v2_name() {
        return area_v2_name == null ? "" : area_v2_name;
    }

    public void setArea_v2_name(String area_v2_name) {
        this.area_v2_name = (area_v2_name == null ? "" : area_v2_name);
    }

    public int getArea_v2_parent_id() {
        return area_v2_parent_id;
    }

    public void setArea_v2_parent_id(int area_v2_parent_id) {
        this.area_v2_parent_id = area_v2_parent_id;
    }

    public String getArea_v2_parent_name() {
        return area_v2_parent_name == null ? "" : area_v2_parent_name;
    }

    public void setArea_v2_parent_name(String area_v2_parent_name) {
        this.area_v2_parent_name = (area_v2_parent_name == null ? "" : area_v2_parent_name);
    }

    public int getAttentions() {
        return attentions;
    }

    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public String getBackground() {
        return background == null ? "" : background;
    }

    public void setBackground(String background) {
        this.background = (background == null ? "" : background);
    }

    public int getBroadcast_type() {
        return broadcast_type;
    }

    public void setBroadcast_type(int broadcast_type) {
        this.broadcast_type = broadcast_type;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = (cover == null ? "" : cover);
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = (face == null ? "" : face);
    }

    public long getFirst_live_time() {
        return first_live_time;
    }

    public void setFirst_live_time(long first_live_time) {
        this.first_live_time = first_live_time;
    }

    public String getHidden_status() {
        return hidden_status == null ? "" : hidden_status;
    }

    public void setHidden_status(String hidden_status) {
        this.hidden_status = (hidden_status == null ? "" : hidden_status);
    }

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = (link == null ? "" : link);
    }

    public String getLive_id() {
        return live_id == null ? "" : live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = (live_id == null ? "" : live_id);
    }

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }

    public String getLive_time() {
        return live_time == null ? "" : live_time;
    }

    public void setLive_time(String live_time) {
        this.live_time = (live_time == null ? "" : live_time);
    }

    public String getLock_status() {
        return lock_status == null ? "" : lock_status;
    }

    public void setLock_status(String lock_status) {
        this.lock_status = (lock_status == null ? "" : lock_status);
    }

    public int getOn_flag() {
        return on_flag;
    }

    public void setOn_flag(int on_flag) {
        this.on_flag = on_flag;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getRoom_shield() {
        return room_shield;
    }

    public void setRoom_shield(int room_shield) {
        this.room_shield = room_shield;
    }

    public int getRoom_silent() {
        return room_silent;
    }

    public void setRoom_silent(int room_silent) {
        this.room_silent = room_silent;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getRound_status() {
        return round_status;
    }

    public void setRound_status(int round_status) {
        this.round_status = round_status;
    }

    public String getShort_id() {
        return short_id == null ? "" : short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = (short_id == null ? "" : short_id);
    }

    public String getSlide_link() {
        return slide_link == null ? "" : slide_link;
    }

    public void setSlide_link(String slide_link) {
        this.slide_link = (slide_link == null ? "" : slide_link);
    }

    public String getTags() {
        return tags == null ? "" : tags;
    }

    public void setTags(String tags) {
        this.tags = (tags == null ? "" : tags);
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public String getTry_time() {
        return try_time == null ? "" : try_time;
    }

    public void setTry_time(String try_time) {
        this.try_time = (try_time == null ? "" : try_time);
    }

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

    public String getUser_cover() {
        return user_cover == null ? "" : user_cover;
    }

    public void setUser_cover(String user_cover) {
        this.user_cover = (user_cover == null ? "" : user_cover);
    }

    public String getVerify() {
        return verify == null ? "" : verify;
    }

    public void setVerify(String verify) {
        this.verify = (verify == null ? "" : verify);
    }

    public int getVirtual() {
        return virtual;
    }

    public void setVirtual(int virtual) {
        this.virtual = virtual;
    }
}
