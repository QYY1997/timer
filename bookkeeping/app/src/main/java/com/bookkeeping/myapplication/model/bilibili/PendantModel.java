package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:21
 * @desc :
 */
public class PendantModel {
    /**
     * pid : 0
     * name :
     * image :
     * expire : 0
     * image_enhance :
     * image_enhance_frame :
     */

    private int pid;
    private String name;
    private String image;//头像框地址
    private int expire;
    private String image_enhance;
    private String image_enhance_frame;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getImage_enhance() {
        return image_enhance == null ? "" : image_enhance;
    }

    public void setImage_enhance(String image_enhance) {
        this.image_enhance = image_enhance;
    }

    public String getImage_enhance_frame() {
        return image_enhance_frame == null ? "" : image_enhance_frame;
    }

    public void setImage_enhance_frame(String image_enhance_frame) {
        this.image_enhance_frame = image_enhance_frame;
    }
}