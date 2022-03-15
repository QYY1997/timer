package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  17:02
 * @desc : 直播动态
 */
public class DynamicLiveingModel {

    /**
     * item : {"at_control":""}
     * live_play_info : {"area_id":192,"area_name":"聊天电台","cover":"https://i0.hdslb.com/bfs/live/new_room_cover/a2072ac63f76d8fd3d3e955be22d4762dc5a5c1e.jpg","link":"https://live.bilibili.com/9038772","live_id":153419014960966580,"live_screen_type":0,"live_start_time":1626677478,"live_status":1,"online":968,"parent_area_id":5,"parent_area_name":"电台","play_type":0,"room_id":9038772,"room_type":0,"title":"cp开售","uid":13036698}
     * live_record_info : null
     * style : 1
     * type : 1
     */

    private ItemPicturesModel item;
    private LivePlayInfoBean live_play_info;
    private Object live_record_info;
    private int style;
    private int type;


    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public LivePlayInfoBean getLive_play_info() {
        return live_play_info;
    }

    public void setLive_play_info(LivePlayInfoBean live_play_info) {
        this.live_play_info = live_play_info;
    }

    public Object getLive_record_info() {
        return live_record_info;
    }

    public void setLive_record_info(Object live_record_info) {
        this.live_record_info = live_record_info;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class LivePlayInfoBean {
        /**
         * area_id : 192
         * area_name : 聊天电台
         * cover : https://i0.hdslb.com/bfs/live/new_room_cover/a2072ac63f76d8fd3d3e955be22d4762dc5a5c1e.jpg
         * link : https://live.bilibili.com/9038772
         * live_id : 153419014960966580
         * live_screen_type : 0
         * live_start_time : 1626677478
         * live_status : 1
         * online : 968
         * parent_area_id : 5
         * parent_area_name : 电台
         * play_type : 0
         * room_id : 9038772
         * room_type : 0
         * title : cp开售
         * uid : 13036698
         */

        private String area_id;
        private String area_name;
        private String cover;
        private String link;
        private String live_id;
        private int live_screen_type;
        private int live_start_time;
        private int live_status;
        private int online;
        private String parent_area_id;
        private String parent_area_name;
        private int play_type;
        private String room_id;
        private int room_type;
        private String title;
        private String uid;

        public String getArea_id() {
            return area_id == null ? "" : area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = (area_id == null ? "" : area_id);
        }

        public String getArea_name() {
            return area_name == null ? "" : area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = (area_name == null ? "" : area_name);
        }

        public String getCover() {
            return cover == null ? "" : cover;
        }

        public void setCover(String cover) {
            this.cover = (cover == null ? "" : cover);
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

        public int getLive_screen_type() {
            return live_screen_type;
        }

        public void setLive_screen_type(int live_screen_type) {
            this.live_screen_type = live_screen_type;
        }

        public int getLive_start_time() {
            return live_start_time;
        }

        public void setLive_start_time(int live_start_time) {
            this.live_start_time = live_start_time;
        }

        public int getLive_status() {
            return live_status;
        }

        public void setLive_status(int live_status) {
            this.live_status = live_status;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getParent_area_id() {
            return parent_area_id == null ? "" : parent_area_id;
        }

        public void setParent_area_id(String parent_area_id) {
            this.parent_area_id = (parent_area_id == null ? "" : parent_area_id);
        }

        public String getParent_area_name() {
            return parent_area_name == null ? "" : parent_area_name;
        }

        public void setParent_area_name(String parent_area_name) {
            this.parent_area_name = (parent_area_name == null ? "" : parent_area_name);
        }

        public int getPlay_type() {
            return play_type;
        }

        public void setPlay_type(int play_type) {
            this.play_type = play_type;
        }

        public String getRoom_id() {
            return room_id == null ? "" : room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = (room_id == null ? "" : room_id);
        }

        public int getRoom_type() {
            return room_type;
        }

        public void setRoom_type(int room_type) {
            this.room_type = room_type;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = (title == null ? "" : title);
        }

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = (uid == null ? "" : uid);
        }
    }
}
