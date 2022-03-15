package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/12  17:43
 * @desc :
 */
public class TagModel {

    /**
     * tag_type : 4
     * sub_type : 2
     * icon : https://i0.hdslb.com/bfs/album/4afb1d524cbd1aa8d4ac97f61e599d067169d646.png
     * text : 热门
     * link : bilibili://pegasus/hotpage?topic_from=topic-card&name=%E7%83%AD%E9%97%A8
     * sub_module : hot
     */

    private int tag_type;
    private int sub_type;
    private String icon;
    private String text;
    private String link;
    private String sub_module;

    public int getTag_type() {
        return tag_type;
    }

    public void setTag_type(int tag_type) {
        this.tag_type = tag_type;
    }

    public int getSub_type() {
        return sub_type;
    }

    public void setSub_type(int sub_type) {
        this.sub_type = sub_type;
    }

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = (icon == null ? "" : icon);
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = (text == null ? "" : text);
    }

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = (link == null ? "" : link);
    }

    public String getSub_module() {
        return sub_module == null ? "" : sub_module;
    }

    public void setSub_module(String sub_module) {
        this.sub_module = (sub_module == null ? "" : sub_module);
    }
}
