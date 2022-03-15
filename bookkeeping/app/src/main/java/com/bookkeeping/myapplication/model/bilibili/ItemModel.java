package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/8  17:43
 * @desc :动态文字内容
 */
public class ItemModel {

    /**
     * rp_id : 544997926430184402
     * uid : 2244668
     * content : 珍爱唱见，远离她的生活！！！
     [tv_思考]

     ps：
     到地方以后的第一件事，打开黄蓝软件看外卖。
     你们呢(⃔ *`꒳´ * )⃕↝
     * ctrl :
     * orig_dy_id : 0
     * pre_dy_id : 0
     * timestamp : 1625730618
     * reply : 24
     */

    private String rp_id;
    private String uid;
    private String content;
    private String ctrl;
    private String orig_dy_id;
    private String pre_dy_id;
    private long timestamp;
    private int reply;
    private List<String> at_uids;//@用户
    private String orig_type;//转发动态类型


    public String getOrig_type() {
        return orig_type == null ? "" : orig_type;
    }

    public void setOrig_type(String orig_type) {
        this.orig_type = (orig_type == null ? "" : orig_type);
    }

    public List<String> getAt_uids() {
        if (at_uids == null) {
            return new ArrayList<>();
        }
        return at_uids;
    }

    public void setAt_uids(List<String> at_uids) {
        this.at_uids = at_uids;
    }

    public String getRp_id() {
        return rp_id == null ? "" : rp_id;
    }

    public void setRp_id(String rp_id) {
        this.rp_id = (rp_id == null ? "" : rp_id);
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = (uid == null ? "" : uid);
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = (content == null ? "" : content);
    }

    public String getCtrl() {
        return ctrl == null ? "" : ctrl;
    }

    public void setCtrl(String ctrl) {
        this.ctrl = (ctrl == null ? "" : ctrl);
    }

    public String getOrig_dy_id() {
        return orig_dy_id == null ? "" : orig_dy_id;
    }

    public void setOrig_dy_id(String orig_dy_id) {
        this.orig_dy_id = (orig_dy_id == null ? "" : orig_dy_id);
    }

    public String getPre_dy_id() {
        return pre_dy_id == null ? "" : pre_dy_id;
    }

    public void setPre_dy_id(String pre_dy_id) {
        this.pre_dy_id = (pre_dy_id == null ? "" : pre_dy_id);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }
}
