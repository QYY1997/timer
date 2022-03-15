package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  16:01
 * @desc :动态图片内容
 */
public class ItemPicturesModel {
    /**
     * at_control :
     * category : daily
     * description : 尤某人直播参加了两个活动都需要小心心[大哭]各位的小心心拜托留一些给我哦[干物妹！小埋_羡慕]真滴十分感谢！！
     https://b23.tv/UEUcwn https://b23.tv/W6QQrv
     * id : 151995540
     * is_fav : 0
     * pictures : [{"img_height":1080,"img_size":2608.181640625,"img_src":"https://i0.hdslb.com/bfs/album/2a2dc9b683cb4ec4f774c9386a55b576c7a3b67a.png","img_tags":null,"img_width":1620}]
     * pictures_count : 1
     * reply : 23
     * role : []
     * settings : {}
     * source : []
     * title :
     * upload_time : 1626415521
     */

    private String at_control;
    private String category;
    private String description;
    private int id;
    private int is_fav;
    private int pictures_count;
    private int reply;
    private SettingsBean settings;
    private String title;
    private int upload_time;
    private List<PicturesModel> pictures;
    private List<String> role;
    private List<String> source;

    public String getAt_control() {
        return at_control == null ? "" : at_control;
    }

    public void setAt_control(String at_control) {
        this.at_control = (at_control == null ? "" : at_control);
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public void setCategory(String category) {
        this.category = (category == null ? "" : category);
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = (description == null ? "" : description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(int is_fav) {
        this.is_fav = is_fav;
    }

    public int getPictures_count() {
        return pictures_count;
    }

    public void setPictures_count(int pictures_count) {
        this.pictures_count = pictures_count;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public SettingsBean getSettings() {
        return settings;
    }

    public void setSettings(SettingsBean settings) {
        this.settings = settings;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public int getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(int upload_time) {
        this.upload_time = upload_time;
    }

    public List<PicturesModel> getPictures() {
        if (pictures == null) {
            return new ArrayList<>();
        }
        return pictures;
    }

    public void setPictures(List<PicturesModel> pictures) {
        this.pictures = pictures;
    }

    public List<String> getRole() {
        if (role == null) {
            return new ArrayList<>();
        }
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<String> getSource() {
        if (source == null) {
            return new ArrayList<>();
        }
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public static class SettingsBean {

        /**
         * copy_forbidden : 0
         */

        private String copy_forbidden;

        public String getCopy_forbidden() {
            return copy_forbidden == null ? "" : copy_forbidden;
        }

        public void setCopy_forbidden(String copy_forbidden) {
            this.copy_forbidden = (copy_forbidden == null ? "" : copy_forbidden);
        }
    }
}
