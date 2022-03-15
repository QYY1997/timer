package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/6/10  15:31
 * @desc :
 */
public class VipModel {

    /**
     * type : 2
     * status : 1
     * due_date : 1633190400000
     * vip_pay_type : 0
     * theme_type : 0
     * label : {"path":"","text":"年度大会员","label_theme":"annual_vip","text_color":"#FFFFFF","bg_style":1,"bg_color":"#FB7299","border_color":""}
     * avatar_subscript : 1
     * nickname_color : #FB7299
     * role : 3
     * avatar_subscript_url : http://i0.hdslb.com/bfs/vip/icon_Certification_big_member_22_3x.png
     */

    private int type;
    private int status;
    private long due_date;
    private int vip_pay_type;
    private int theme_type;
    private LabelBean label;
    private int avatar_subscript;
    private String nickname_color;
    private int role;
    private String avatar_subscript_url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDue_date() {
        return due_date;
    }

    public void setDue_date(long due_date) {
        this.due_date = due_date;
    }

    public int getVip_pay_type() {
        return vip_pay_type;
    }

    public void setVip_pay_type(int vip_pay_type) {
        this.vip_pay_type = vip_pay_type;
    }

    public int getTheme_type() {
        return theme_type;
    }

    public void setTheme_type(int theme_type) {
        this.theme_type = theme_type;
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
        this.nickname_color = nickname_color == null ? "" : nickname_color;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAvatar_subscript_url() {
        return avatar_subscript_url == null ? "" : avatar_subscript_url;
    }

    public void setAvatar_subscript_url(String avatar_subscript_url) {
        this.avatar_subscript_url = avatar_subscript_url == null ? "" : avatar_subscript_url;
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
            this.path = path == null ? "" : path;
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = text == null ? "" : text;
        }

        public String getLabel_theme() {
            return label_theme == null ? "" : label_theme;
        }

        public void setLabel_theme(String label_theme) {
            this.label_theme = label_theme == null ? "" : label_theme;
        }

        public String getText_color() {
            return text_color == null ? "" : text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color == null ? "" : text_color;
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
            this.bg_color = bg_color == null ? "" : bg_color;
        }

        public String getBorder_color() {
            return border_color == null ? "" : border_color;
        }

        public void setBorder_color(String border_color) {
            this.border_color = border_color == null ? "" : border_color;
        }
    }
}
