package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:15
 * @desc :
 */
public class DescModel {

    /**
     * uid : 3306866
     * type : 2
     * rid : 150202589
     * acl : 0
     * view : 6419
     * repost : 0
     * comment : 43
     * like : 318
     * is_liked : 0
     * dynamic_id : 545275862352241716
     * timestamp : 1625795330
     * pre_dy_id : 0
     * orig_dy_id : 0
     * orig_type : 0
     * user_profile : {}
     * uid_type : 1
     * stype : 0
     * r_type : 1
     * inner_id : 0
     * status : 1
     * dynamic_id_str : 545275862352241716
     * pre_dy_id_str : 0
     * orig_dy_id_str : 0
     * rid_str : 150202589
     */

    private String uid;
    private int type;
    private String rid;
    private int acl;
    private int view;
    private int repost;
    private int comment;
    private int like;
    private int is_liked;
    private String dynamic_id;
    private long timestamp;
    private String pre_dy_id;
    private String orig_dy_id;
    private int orig_type;
    private OriginUserModel user_profile;
    private int uid_type;
    private int stype;
    private int r_type;
    private String inner_id;
    private int status;
    private String dynamic_id_str;
    private String pre_dy_id_str;
    private String orig_dy_id_str;
    private String rid_str;
    private String bvid;
    private DescModel origin;

    public String getBvid() {
        return bvid == null ? "" : bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = (bvid == null ? "" : bvid);
    }

    public DescModel getOrigin() {
        return origin;
    }

    public void setOrigin(DescModel origin) {
        this.origin = origin;
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = (uid == null ? "" : uid);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRid() {
        return rid == null ? "" : rid;
    }

    public void setRid(String rid) {
        this.rid = (rid == null ? "" : rid);
    }

    public int getAcl() {
        return acl;
    }

    public void setAcl(int acl) {
        this.acl = acl;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getRepost() {
        return repost;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(int is_liked) {
        this.is_liked = is_liked;
    }

    public String getDynamic_id() {
        return dynamic_id == null ? "" : dynamic_id;
    }

    public void setDynamic_id(String dynamic_id) {
        this.dynamic_id = (dynamic_id == null ? "" : dynamic_id);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPre_dy_id() {
        return pre_dy_id == null ? "" : pre_dy_id;
    }

    public void setPre_dy_id(String pre_dy_id) {
        this.pre_dy_id = (pre_dy_id == null ? "" : pre_dy_id);
    }

    public String getOrig_dy_id() {
        return orig_dy_id == null ? "" : orig_dy_id;
    }

    public void setOrig_dy_id(String orig_dy_id) {
        this.orig_dy_id = (orig_dy_id == null ? "" : orig_dy_id);
    }

    public int getOrig_type() {
        return orig_type;
    }

    public void setOrig_type(int orig_type) {
        this.orig_type = orig_type;
    }

    public OriginUserModel getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(OriginUserModel user_profile) {
        this.user_profile = user_profile;
    }

    public int getUid_type() {
        return uid_type;
    }

    public void setUid_type(int uid_type) {
        this.uid_type = uid_type;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public int getR_type() {
        return r_type;
    }

    public void setR_type(int r_type) {
        this.r_type = r_type;
    }

    public String getInner_id() {
        return inner_id == null ? "" : inner_id;
    }

    public void setInner_id(String inner_id) {
        this.inner_id = (inner_id == null ? "" : inner_id);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDynamic_id_str() {
        return dynamic_id_str == null ? "" : dynamic_id_str;
    }

    public void setDynamic_id_str(String dynamic_id_str) {
        this.dynamic_id_str = (dynamic_id_str == null ? "" : dynamic_id_str);
    }

    public String getPre_dy_id_str() {
        return pre_dy_id_str == null ? "" : pre_dy_id_str;
    }

    public void setPre_dy_id_str(String pre_dy_id_str) {
        this.pre_dy_id_str = (pre_dy_id_str == null ? "" : pre_dy_id_str);
    }

    public String getOrig_dy_id_str() {
        return orig_dy_id_str == null ? "" : orig_dy_id_str;
    }

    public void setOrig_dy_id_str(String orig_dy_id_str) {
        this.orig_dy_id_str = (orig_dy_id_str == null ? "" : orig_dy_id_str);
    }

    public String getRid_str() {
        return rid_str == null ? "" : rid_str;
    }

    public void setRid_str(String rid_str) {
        this.rid_str = (rid_str == null ? "" : rid_str);
    }
}
