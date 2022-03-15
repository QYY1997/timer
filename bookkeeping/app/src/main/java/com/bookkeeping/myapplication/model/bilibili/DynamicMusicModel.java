package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  15:49
 * @desc :音乐动态 type:256
 */
public class DynamicMusicModel {

    /**
     * author : 我是怪异君
     * cover : https://i0.hdslb.com/bfs/music/4b4fa92396a28e6f3e068c07b78e0c78af3c410c.jpg
     * ctime : 1626418075000
     * id : 2428381
     * intro : 怪异电台59期

     不用多说
     经典IP福尔摩斯
     除了搞笑视频以外
     这次正儿八经的聊聊
     福尔摩斯改编动画
     * item : {"at_control":""}
     * playCnt : 1391
     * replyCnt : 7
     * schema : bilibili://music/detail/2428381?name=%E6%80%AA%E5%BC%82%E7%94%B5%E5%8F%B0Vol.60+%E7%BD%91%E7%BB%9C%E6%90%9E%E7%AC%91%E7%B4%A0%E6%9D%90%EF%BC%9F%E5%AE%AB%E5%B4%8E%E9%AA%8F%E5%AF%BC%E6%BC%94%E4%BD%9C%E5%93%81%EF%BC%9F%E8%81%8A%E8%81%8A%E9%82%A3%E4%BA%9B%E7%A6%8F%E5%B0%94%E6%91%A9%E6%96%AF%E6%94%B9%E7%BC%96%E5%8A%A8%E7%94%BB%EF%BC%81&uperName=&cover_url=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Fmusic%2F4b4fa92396a28e6f3e068c07b78e0c78af3c410c.jpg&upperId=&author=%E6%88%91%E6%98%AF%E6%80%AA%E5%BC%82%E5%90%9B
     * title : 怪异电台Vol.60 网络搞笑素材？宫崎骏导演作品？聊聊那些福尔摩斯改编动画！
     * typeInfo : 有声节目 · 其他
     * upId : 4408538
     * upper : 我是怪异君
     * upperAvatar : https://i2.hdslb.com/bfs/face/4b3bdd3188d7b8b9200e16c70cba01c25b818a26.jpg
     */

    private String author;
    private String cover;
    private long ctime;
    private String id;
    private String intro;
    private ItemPicturesModel item;
    private int playCnt;
    private int replyCnt;
    private String schema;
    private String title;
    private String typeInfo;
    private String upId;
    private String upper;
    private String upperAvatar;

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = (author == null ? "" : author);
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = (cover == null ? "" : cover);
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getIntro() {
        return intro == null ? "" : intro;
    }

    public void setIntro(String intro) {
        this.intro = (intro == null ? "" : intro);
    }

    public ItemPicturesModel getItem() {
        return item;
    }

    public void setItem(ItemPicturesModel item) {
        this.item = item;
    }

    public int getPlayCnt() {
        return playCnt;
    }

    public void setPlayCnt(int playCnt) {
        this.playCnt = playCnt;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    public String getSchema() {
        return schema == null ? "" : schema;
    }

    public void setSchema(String schema) {
        this.schema = (schema == null ? "" : schema);
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public String getTypeInfo() {
        return typeInfo == null ? "" : typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = (typeInfo == null ? "" : typeInfo);
    }

    public String getUpId() {
        return upId == null ? "" : upId;
    }

    public void setUpId(String upId) {
        this.upId = (upId == null ? "" : upId);
    }

    public String getUpper() {
        return upper == null ? "" : upper;
    }

    public void setUpper(String upper) {
        this.upper = (upper == null ? "" : upper);
    }

    public String getUpperAvatar() {
        return upperAvatar == null ? "" : upperAvatar;
    }

    public void setUpperAvatar(String upperAvatar) {
        this.upperAvatar = (upperAvatar == null ? "" : upperAvatar);
    }
}
