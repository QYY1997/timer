package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:19
 * @desc :
 */
public class VipPersonModel {
    /**
     * vipType : 2
     * vipDueDate : 1629216000000
     * dueRemark :
     * accessStatus : 0
     * vipStatus : 1
     * vipStatusWarn :
     * themeType : 0
     * label : {"path":"","text":"年度大会员","label_theme":"annual_vip","text_color":"#FFFFFF","bg_style":1,"bg_color":"#FB7299","border_color":""}
     * avatar_subscript : 1
     * nickname_color : #FB7299
     */

    private int vipType; //会员类型 1：普通会员 2:年度大会员
    private long vipDueDate;//会员到期时间
    private String dueRemark;
    private int accessStatus;
    private int vipStatus;
    private String vipStatusWarn;
    private int themeType;
    private LabelBean label;
    private String avatar_subscript_url;//会员图标地址
    private int avatar_subscript;
    private String nickname_color;

    public String getAvatar_subscript_url() {
        return avatar_subscript_url == null ? "" : avatar_subscript_url;
    }

    public void setAvatar_subscript_url(String avatar_subscript_url) {
        this.avatar_subscript_url = avatar_subscript_url == null ? "" : avatar_subscript_url;
    }

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public long getVipDueDate() {
        return vipDueDate;
    }

    public void setVipDueDate(long vipDueDate) {
        this.vipDueDate = vipDueDate;
    }

    public String getDueRemark() {
        return dueRemark == null ? "" : dueRemark;
    }

    public void setDueRemark(String dueRemark) {
        this.dueRemark = dueRemark;
    }

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public int getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getVipStatusWarn() {
        return vipStatusWarn == null ? "" : vipStatusWarn;
    }

    public void setVipStatusWarn(String vipStatusWarn) {
        this.vipStatusWarn = vipStatusWarn;
    }

    public int getThemeType() {
        return themeType;
    }

    public void setThemeType(int themeType) {
        this.themeType = themeType;
    }

    public LabelBean getLabel() {
        return label==null?new LabelBean():label;
    }

    public void setLabel(LabelBean label) {
        this.label = label;
    }

    public int getAvatar_subscript() {
        return avatar_subscript;
    }

    public void setAvatar_subscript(int avatar_subscript) {
        this.avatar_subscript = avatar_subscript;
    }

    public String getNickname_color() {
        return nickname_color == null ? "" : nickname_color;
    }

    public void setNickname_color(String nickname_color) {
        this.nickname_color = nickname_color;
    }

    public static class LabelBean {
        /**
         * path :
         * text : 年度大会员
         * label_theme : annual_vip
         * text_color : #FFFFFF
         * bg_style : 1
         * bg_color : #FB7299
         * border_color :
         */

        private String path;
        private String text;
        private String label_theme;
        private String text_color;
        private int bg_style;
        private String bg_color;
        private String border_color;

        public String getPath() {
            return path == null ? "" : path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLabel_theme() {
            return label_theme == null ? "" : label_theme;
        }

        public void setLabel_theme(String label_theme) {
            this.label_theme = label_theme;
        }

        public String getText_color() {
            return text_color == null ? "" : text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }

        public int getBg_style() {
            return bg_style;
        }

        public void setBg_style(int bg_style) {
            this.bg_style = bg_style;
        }

        public String getBg_color() {
            return bg_color == null ? "" : bg_color;
        }

        public void setBg_color(String bg_color) {
            this.bg_color = bg_color;
        }

        public String getBorder_color() {
            return border_color == null ? "" : border_color;
        }

        public void setBorder_color(String border_color) {
            this.border_color = border_color;
        }
    }
}