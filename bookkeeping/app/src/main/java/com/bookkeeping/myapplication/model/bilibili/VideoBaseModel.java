package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/4/6  11:14
 * @desc :
 */
public class VideoBaseModel {

    /**
     * cid : 306397374
     * page : 1
     * from : vupload
     * part : 蝙蝠侠翻拍雀巢配音版
     * duration : 131
     * vid :
     * weblink :
     * dimension : {"width":3840,"height":2160,"rotate":0}
     */

    private String cid;
    private int page;
    private String from;
    private String part;
    private int duration;
    private String vid;
    private String weblink;
    private DimensionModel dimension;

    public String getCid() {
        return cid == null ? "" : cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? "" : cid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getFrom() {
        return from == null ? "" : from;
    }

    public void setFrom(String from) {
        this.from = from == null ? "" : from;
    }

    public String getPart() {
        return part == null ? "" : part;
    }

    public void setPart(String part) {
        this.part = part == null ? "" : part;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVid() {
        return vid == null ? "" : vid;
    }

    public void setVid(String vid) {
        this.vid = vid == null ? "" : vid;
    }

    public String getWeblink() {
        return weblink == null ? "" : weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink == null ? "" : weblink;
    }

    public DimensionModel getDimension() {
        return dimension;
    }

    public void setDimension(DimensionModel dimension) {
        this.dimension = dimension;
    }
}
