package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  16:04
 * @desc :专栏动态 type:64
 */
public class DynamicColumnModel {

    /**
     * act_id : 0
     * apply_time :
     * authenMark : null
     * author : {}
     * banner_url : https://i0.hdslb.com/bfs/article/f2907d47e9c7bf3c0b6baf965ecdd9af634d08cd.jpg
     * categories : [{"id":1,"name":"游戏","parent_id":0},{"id":7,"name":"电子竞技","parent_id":1}]
     * category : {}
     * check_time :
     * cover_avid : 0
     * ctime : 1626406023
     * dispute : {}
     * id : 12177780
     * image_urls : ["https://i0.hdslb.com/bfs/article/8cba526ef6497df0d6c5f59f6ac8220ffaffc752.png"]
     * is_like : false
     * item : {"at_control":""}
     * list : {}
     * media : {}
     * origin_image_urls : ["https://i0.hdslb.com/bfs/article/fdf494c8e383961d24347dbdee7006724df1cbcc.png"]
     * original : 0
     * publish_time : 1626408654
     * reprint : 0
     * state : 0
     * stats : {}
     * summary : 来自早上8:30，由Coin Concede发布的的新卡：术士稀有法术（暗影）1费：对一个随从造成2点伤害。如果该随从死亡，为你的英雄恢复4点生命值。17/135沿用以前的习惯，兽兽的新卡速递现在变为了专栏发布，这样相对于之前的动态发布有三个好处：①：大家可以通过左右滑动手机屏幕查看之前的新卡（电脑端可以点击上一篇按钮） ，也可以前往贫瘠之地的锤炼的专栏文集②：专栏有一次可修改的机会，避免兽兽的发布有误 ③：可以为兽兽增加专栏数据，兽兽也可以获得一些专栏浏览 所以大家看完新卡也可以给兽兽点下推荐
     * template_id : 4
     * title : 暴风城下的集结新卡预览（17/135）
     * top_video_info : null
     * type : 0
     * words : 256
     */

    private int act_id;
    private String apply_time;
    private String authenMark;
    private AuthorBean author;
    private String banner_url;
    private CategoriesModel category;
    private String check_time;
    private int cover_avid;
    private long ctime;
    private DisputeBean dispute;
    private String id;
    private boolean is_like;
    private ItemPicturesModel item;
    private ListBean list;
    private MediaBean media;
    private int original;
    private int publish_time;
    private int reprint;
    private int state;
    private StatVideoModel stats;
    private String summary;
    private int template_id;
    private String title;
    private Object top_video_info;
    private int type;
    private int words;
    private List<CategoriesModel> categories;
    private List<String> image_urls;
    private List<String> origin_image_urls;

    public int getAct_id() {
        return act_id;
    }

    public void setAct_id(int act_id) {
        this.act_id = act_id;
    }

    public String getApply_time() {
        return apply_time == null ? "" : apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = (apply_time == null ? "" : apply_time);
    }

    public String getAuthenMark() {
        return authenMark == null ? "" : authenMark;
    }

    public void setAuthenMark(String authenMark) {
        this.authenMark = (authenMark == null ? "" : authenMark);
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public String getBanner_url() {
        return banner_url == null ? "" : banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = (banner_url == null ? "" : banner_url);
    }

    public CategoriesModel getCategory() {
        return category;
    }

    public void setCategory(CategoriesModel category) {
        this.category = category;
    }

    public String getCheck_time() {
        return check_time == null ? "" : check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = (check_time == null ? "" : check_time);
    }

    public int getCover_avid() {
        return cover_avid;
    }

    public void setCover_avid(int cover_avid) {
        this.cover_avid = cover_avid;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public DisputeBean getDispute() {
        return dispute;
    }

    public void setDispute(DisputeBean dispute) {
        this.dispute = dispute;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public boolean isIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(int publish_time) {
        this.publish_time = publish_time;
    }

    public int getReprint() {
        return reprint;
    }

    public void setReprint(int reprint) {
        this.reprint = reprint;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public StatVideoModel getStats() {
        return stats;
    }

    public void setStats(StatVideoModel stats) {
        this.stats = stats;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public void setSummary(String summary) {
        this.summary = (summary == null ? "" : summary);
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public Object getTop_video_info() {
        return top_video_info;
    }

    public void setTop_video_info(Object top_video_info) {
        this.top_video_info = top_video_info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public List<CategoriesModel> getCategories() {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<CategoriesModel> categories) {
        this.categories = categories;
    }

    public List<String> getImage_urls() {
        if (image_urls == null) {
            return new ArrayList<>();
        }
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public List<String> getOrigin_image_urls() {
        if (origin_image_urls == null) {
            return new ArrayList<>();
        }
        return origin_image_urls;
    }

    public void setOrigin_image_urls(List<String> origin_image_urls) {
        this.origin_image_urls = origin_image_urls;
    }

    public static class AuthorBean {

        /**
         * face : https://i2.hdslb.com/bfs/face/9b2cd32d3aa50c8bde06b430ffc57ee89ff81c6c.jpg
         * mid : 17712476
         * name : 吃節操の萌萌兽w
         * nameplate : {}
         * official_verify : {}
         * pendant : {}
         * vip : {}
         */

        private String face;
        private String mid;
        private String name;
        private NameplateModel nameplate;
        private OfficialVerifyModel official_verify;
        private PendantModel pendant;
        private VipModel vip;

        public String getFace() {
            return face == null ? "" : face;
        }

        public void setFace(String face) {
            this.face = (face == null ? "" : face);
        }

        public String getMid() {
            return mid == null ? "" : mid;
        }

        public void setMid(String mid) {
            this.mid = (mid == null ? "" : mid);
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = (name == null ? "" : name);
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

        public PendantModel getPendant() {
            return pendant;
        }

        public void setPendant(PendantModel pendant) {
            this.pendant = pendant;
        }

        public VipModel getVip() {
            return vip;
        }

        public void setVip(VipModel vip) {
            this.vip = vip;
        }
    }

    public static class DisputeBean {

        /**
         * dispute :
         * dispute_url :
         */

        private String dispute;
        private String dispute_url;

        public String getDispute() {
            return dispute == null ? "" : dispute;
        }

        public void setDispute(String dispute) {
            this.dispute = (dispute == null ? "" : dispute);
        }

        public String getDispute_url() {
            return dispute_url == null ? "" : dispute_url;
        }

        public void setDispute_url(String dispute_url) {
            this.dispute_url = (dispute_url == null ? "" : dispute_url);
        }
    }

    public static class ListBean {

        /**
         * apply_time :
         * articles_count : 0
         * check_time :
         * ctime : 1622568723
         * id : 421089
         * image_url :
         * mid : 17712476
         * name : 炉石传说新卡预览w
         * publish_time : 1626408654
         * read : 0
         * reason :
         * state : 1
         * summary :
         * update_time : 1626415028
         * words : 54813
         */

        private String apply_time;
        private int articles_count;
        private String check_time;
        private long ctime;
        private int id;
        private String image_url;
        private String mid;
        private String name;
        private long publish_time;
        private int read;
        private String reason;
        private int state;
        private String summary;
        private long update_time;
        private int words;

        public String getApply_time() {
            return apply_time == null ? "" : apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = (apply_time == null ? "" : apply_time);
        }

        public int getArticles_count() {
            return articles_count;
        }

        public void setArticles_count(int articles_count) {
            this.articles_count = articles_count;
        }

        public String getCheck_time() {
            return check_time == null ? "" : check_time;
        }

        public void setCheck_time(String check_time) {
            this.check_time = (check_time == null ? "" : check_time);
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_url() {
            return image_url == null ? "" : image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = (image_url == null ? "" : image_url);
        }

        public String getMid() {
            return mid == null ? "" : mid;
        }

        public void setMid(String mid) {
            this.mid = (mid == null ? "" : mid);
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = (name == null ? "" : name);
        }

        public long getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(long publish_time) {
            this.publish_time = publish_time;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public String getReason() {
            return reason == null ? "" : reason;
        }

        public void setReason(String reason) {
            this.reason = (reason == null ? "" : reason);
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSummary() {
            return summary == null ? "" : summary;
        }

        public void setSummary(String summary) {
            this.summary = (summary == null ? "" : summary);
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }
    }

    public static class MediaBean {

        /**
         * area :
         * cover :
         * media_id : 0
         * score : 0
         * season_id : 0
         * spoiler : 0
         * title :
         * type_id : 0
         * type_name :
         */

        private String area;
        private String cover;
        private int media_id;
        private int score;
        private int season_id;
        private int spoiler;
        private String title;
        private int type_id;
        private String type_name;

        public String getArea() {
            return area == null ? "" : area;
        }

        public void setArea(String area) {
            this.area = (area == null ? "" : area);
        }

        public String getCover() {
            return cover == null ? "" : cover;
        }

        public void setCover(String cover) {
            this.cover = (cover == null ? "" : cover);
        }

        public int getMedia_id() {
            return media_id;
        }

        public void setMedia_id(int media_id) {
            this.media_id = media_id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSeason_id() {
            return season_id;
        }

        public void setSeason_id(int season_id) {
            this.season_id = season_id;
        }

        public int getSpoiler() {
            return spoiler;
        }

        public void setSpoiler(int spoiler) {
            this.spoiler = spoiler;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = (title == null ? "" : title);
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name == null ? "" : type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = (type_name == null ? "" : type_name);
        }
    }
}
