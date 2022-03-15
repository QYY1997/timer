package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/26  17:38
 * @desc :
 */
public class DynamicDramaModel {

    /**
     * aid : 804263922
     * apiSeasonInfo : {"bgm_type":1,"cover":"https://i0.hdslb.com/bfs/bangumi/image/aec438b37e0e2d7b3b2d800e38c515486909a46e.png","is_finish":0,"season_id":38224,"title":"入间同学入魔了 第二季","total_count":-1,"ts":1627290767,"type_name":"番剧"}
     * bullet_count : 10449
     * cover : https://i0.hdslb.com/bfs/archive/d24cd1d4734e6351b620e08b36d938e87e036582.jpg
     * episode_id : 403369
     * index : 11
     * index_title : 终末考试 | 女生会谈
     * item : {}
     * new_desc : 第11话 终末考试 | 女生会谈
     * online_finish : 0
     * play_count : 813211
     * reply_count : 1557
     * url : https://www.bilibili.com/bangumi/play/ep403369
     */

    private String aid;
    private ApiSeasonInfoBean apiSeasonInfo;
    private int bullet_count;
    private String cover;
    private String episode_id;
    private String index;
    private String index_title;
    private ItemPicturesModel item;
    private String new_desc;
    private int online_finish;
    private int play_count;
    private int reply_count;
    private String url;

    public String getAid() {
        return aid == null ? "" : aid;
    }

    public void setAid(String aid) {
        this.aid = (aid == null ? "" : aid);
    }

    public ApiSeasonInfoBean getApiSeasonInfo() {
        return apiSeasonInfo;
    }

    public void setApiSeasonInfo(ApiSeasonInfoBean apiSeasonInfo) {
        this.apiSeasonInfo = apiSeasonInfo;
    }

    public int getBullet_count() {
        return bullet_count;
    }

    public void setBullet_count(int bullet_count) {
        this.bullet_count = bullet_count;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = (cover == null ? "" : cover);
    }

    public String getEpisode_id() {
        return episode_id == null ? "" : episode_id;
    }

    public void setEpisode_id(String episode_id) {
        this.episode_id = (episode_id == null ? "" : episode_id);
    }

    public String getIndex() {
        return index == null ? "" : index;
    }

    public void setIndex(String index) {
        this.index = (index == null ? "" : index);
    }

    public String getIndex_title() {
        return index_title == null ? "" : index_title;
    }

    public void setIndex_title(String index_title) {
        this.index_title = (index_title == null ? "" : index_title);
    }

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public String getNew_desc() {
        return new_desc == null ? "" : new_desc;
    }

    public void setNew_desc(String new_desc) {
        this.new_desc = (new_desc == null ? "" : new_desc);
    }

    public int getOnline_finish() {
        return online_finish;
    }

    public void setOnline_finish(int online_finish) {
        this.online_finish = online_finish;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = (url == null ? "" : url);
    }

    public static class ApiSeasonInfoBean {

        /**
         * bgm_type : 1
         * cover : https://i0.hdslb.com/bfs/bangumi/image/aec438b37e0e2d7b3b2d800e38c515486909a46e.png
         * is_finish : 0
         * season_id : 38224
         * title : 入间同学入魔了 第二季
         * total_count : -1
         * ts : 1627290767
         * type_name : 番剧
         */

        private int bgm_type;
        private String cover;
        private int is_finish;
        private String season_id;
        private String title;
        private int total_count;
        private long ts;
        private String type_name;

        public int getBgm_type() {
            return bgm_type;
        }

        public void setBgm_type(int bgm_type) {
            this.bgm_type = bgm_type;
        }

        public String getCover() {
            return cover == null ? "" : cover;
        }

        public void setCover(String cover) {
            this.cover = (cover == null ? "" : cover);
        }

        public int getIs_finish() {
            return is_finish;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public String getSeason_id() {
            return season_id == null ? "" : season_id;
        }

        public void setSeason_id(String season_id) {
            this.season_id = (season_id == null ? "" : season_id);
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = (title == null ? "" : title);
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public String getType_name() {
            return type_name == null ? "" : type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = (type_name == null ? "" : type_name);
        }
    }

}
