package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/23  11:19
 * @desc :
 */
public class DynamicLiveCloseModel {

    /**
     * area : 10
     * area_v2_id : 21
     * area_v2_name : 视频唱见
     * area_v2_parent_id : 1
     * area_v2_parent_name : 娱乐
     * attentions : 0
     * background :
     * broadcast_type : 0
     * cover : https:\/\/i0.hdslb.com\/bfs\/live\/new_room_cover\/8413c2868ae62600729f69b5ddbd44aee173309b.jpg
     * face : https:\/\/i2.hdslb.com\/bfs\/face\/73893617aeaf3511739619efa70bcd8a163fc57a.jpg
     * first_live_time : 1620566112
     * hidden_status : 0000-00-00 00:00:00
     * item : {}
     * link : https:\/\/live.bilibili.com\/23082715?accept_quality=%5B10000%5D&broadcast_type=0¤t_qn=10000¤t_quality=10000&is_room_feed=1&live_play_network=other&p2p_type=0&playurl_h264=http%3A%2F%2Fd1--cn-gotcha05.bilivideo.com%2Flive-bvc%2F259509%2Flive_1241505866_47296157.flv%3Fexpires%3D1627013574%26len%3D0%26oi%3D0%26pt%3D%26qn%3D150%26trid%3D1000f28e3d24af9b4fdfa4d6e2ee82db48b5%26sigparams%3Dcdn%2Cexpires%2Clen%2Coi%2Cpt%2Cqn%2Ctrid%26cdn%3Dcn-gotcha05%26sign%3Daca2f563dfbfc441824b9366774ca926%26p2p_type%3D0%26src%3D8%26sl%3D1%26sk%3D18232a4fcc82e4c037c33d7e083619b4&playurl_h265=&quality_description=%5B%7B%22qn%22%3A10000%2C%22desc%22%3A%22%E5%8E%9F%E7%94%BB%22%7D%5D&parent_area_id=1&area_id=21
     * live_id : 0
     * live_status : 2
     * live_time : 0000-00-00 00:00:00
     * lock_status : 0000-00-00 00:00:00
     * on_flag : 1
     * online : 97907
     * room_shield : 0
     * room_silent : 0
     * roomid : 23082715
     * round_status : 1
     * short_id : 0
     * slide_link : https:\/\/live.bilibili.com\/23082715?broadcast_type=0&is_room_feed=1
     * tags : 说单口相声的唱见,爱好戏腔
     * title : 汉服小姐姐带你体验夏日清凉季~
     * try_time : 0000-00-00 00:00:00
     * uid : 1241505866
     * uname : -鹤尤-
     * user_cover : https:\/\/i0.hdslb.com\/bfs\/live\/new_room_cover\/8413c2868ae62600729f69b5ddbd44aee173309b.jpg
     * verify :
     * virtual : 1
     */

    private int area;
    private int area_v2_id;
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
    private String roomid;
    private int round_status;
    private int short_id;
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

    public int getArea_v2_id() {
        return area_v2_id;
    }

    public void setArea_v2_id(int area_v2_id) {
        this.area_v2_id = area_v2_id;
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

    public String getRoomid() {
        return roomid == null ? "" : roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = (roomid == null ? "" : roomid);
    }

    public int getRound_status() {
        return round_status;
    }

    public void setRound_status(int round_status) {
        this.round_status = round_status;
    }

    public int getShort_id() {
        return short_id;
    }

    public void setShort_id(int short_id) {
        this.short_id = short_id;
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
