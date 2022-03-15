package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  17:48
 * @desc :
 */
public  class EmoteModel {
    /**
     * id : 27
     * package_id : 1
     * state : 0
     * type : 1
     * attr : 0
     * text : [滑稽]
     * url : http://i0.hdslb.com/bfs/emote/d15121545a99ac46774f1f4465b895fe2d1411c3.png
     * meta : {"size":1,"suggest":[""]}
     * mtime : 1615789854
     */

    private String emoji_name;
    private String id;
    private String package_id;
    private int state;
    private int type;
    private int attr;
    private String text;
    private String url;
    private MetaBean meta;
    private int mtime;
    private List<EmoteModel> emote;
    private FlagsBean flags;

    public String getEmoji_name() {
        return emoji_name == null ? "" : emoji_name;
    }

    public void setEmoji_name(String emoji_name) {
        this.emoji_name = (emoji_name == null ? "" : emoji_name);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getPackage_id() {
        return package_id == null ? "" : package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = (package_id == null ? "" : package_id);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAttr() {
        return attr;
    }

    public void setAttr(int attr) {
        this.attr = attr;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MetaBean getMeta() {
        return meta == null ? new MetaBean() : meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public int getMtime() {
        return mtime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public FlagsBean getFlags() {
        return flags == null ? new FlagsBean() : flags;
    }

    public void setFlags(FlagsBean flags) {
        this.flags = flags;
    }

    public List<EmoteModel> getEmote() {
        if (emote == null) {
            return new ArrayList<>();
        }
        return emote;
    }

    public void setEmote(List<EmoteModel> emote) {
        this.emote = emote;
    }

    public static class FlagsBean {
        /**
         * added : true
         */

        private boolean added;

        public boolean isAdded() {
            return added;
        }

        public void setAdded(boolean added) {
            this.added = added;
        }
    }

    public static class MetaBean {
        /**
         * size : 1
         * suggest : [""]
         */

        private int size;
        private List<String> suggest;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<String> getSuggest() {
            return suggest == null ? new ArrayList<String>() : suggest;
        }

        public void setSuggest(List<String> suggest) {
            this.suggest = suggest;
        }
    }
}
