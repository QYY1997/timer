package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  17:47
 * @desc :
 */
public class PicturesModel {
    /**
     * img_height : 1080
     * img_size : 2608.181640625
     * img_src : https://i0.hdslb.com/bfs/album/2a2dc9b683cb4ec4f774c9386a55b576c7a3b67a.png
     * img_tags : null
     * img_width : 1620
     */

    private int img_height;
    private double img_size;
    private String img_src;
    private String img_tags;
    private int img_width;

    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public double getImg_size() {
        return img_size;
    }

    public void setImg_size(double img_size) {
        this.img_size = img_size;
    }

    public String getImg_src() {
        return img_src == null ? "" : img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = (img_src == null ? "" : img_src);
    }

    public String getImg_tags() {
        return img_tags == null ? "" : img_tags;
    }

    public void setImg_tags(String img_tags) {
        this.img_tags = (img_tags == null ? "" : img_tags);
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }
}
