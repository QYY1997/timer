package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  15:37
 * @desc :带图动态 type:2
 */
public class DynamicPicturesModel {

    /**
     * item : {"at_control":"","category":"daily","description":"尤某人直播参加了两个活动都需要小心心[大哭]各位的小心心拜托留一些给我哦[干物妹！小埋_羡慕]真滴十分感谢！！\nhttps://b23.tv/UEUcwn https://b23.tv/W6QQrv","id":151995540,"is_fav":0,"pictures":[{"img_height":1080,"img_size":2608.181640625,"img_src":"https://i0.hdslb.com/bfs/album/2a2dc9b683cb4ec4f774c9386a55b576c7a3b67a.png","img_tags":null,"img_width":1620}],"pictures_count":1,"reply":23,"role":[],"settings":{},"source":[],"title":"","upload_time":1626415521}
     * user : {"head_url":"https://i2.hdslb.com/bfs/face/73893617aeaf3511739619efa70bcd8a163fc57a.jpg","name":"-鹤尤-","uid":1241505866,"vip":{}}
     */

    private ItemPicturesModel item;
    private UserBean user;

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


    public static class UserBean {
        /**
         * head_url : https://i2.hdslb.com/bfs/face/73893617aeaf3511739619efa70bcd8a163fc57a.jpg
         * name : -鹤尤-
         * uid : 1241505866
         * vip : {}
         */

        private String head_url;
        private String name;
        private int uid;
        private VipModel vip;

        public String getHead_url() {
            return head_url == null ? "" : head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = (head_url == null ? "" : head_url);
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = (name == null ? "" : name);
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public VipModel getVip() {
            return vip;
        }

        public void setVip(VipModel vip) {
            this.vip = vip;
        }
    }
}
