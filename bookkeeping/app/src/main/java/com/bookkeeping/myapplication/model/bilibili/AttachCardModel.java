package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/12  16:58
 * @desc :
 */
public class AttachCardModel {
    /**
     * type : game
     * head_text : 相关游戏
     * cover_url : https://i0.hdslb.com/bfs/game/f15afdeae1ff7d3e0a045847a5cf4741cfd7c184.png
     * cover_type : 1
     * title : 碧蓝航线
     * desc_first : 养成/美少女/即时海战
     * desc_second : 4周年版本
     * jump_url : https://www.biligame.com/detail?id=97&sourceFrom=1005
     * button : {"type":1,"jump_style":{"text":"进入"},"jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005"}
     * oid_str : 97
     */

    private String type;
    private String head_text;
    private String cover_url;
    private int cover_type;
    private String title;
    private String desc_first;
    private String desc_second;
    private String jump_url;
    private ButtonBean button;
    private String oid_str;

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public String getHead_text() {
        return head_text == null ? "" : head_text;
    }

    public void setHead_text(String head_text) {
        this.head_text = (head_text == null ? "" : head_text);
    }

    public String getCover_url() {
        return cover_url == null ? "" : cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = (cover_url == null ? "" : cover_url);
    }

    public int getCover_type() {
        return cover_type;
    }

    public void setCover_type(int cover_type) {
        this.cover_type = cover_type;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public String getDesc_first() {
        return desc_first == null ? "" : desc_first;
    }

    public void setDesc_first(String desc_first) {
        this.desc_first = (desc_first == null ? "" : desc_first);
    }

    public String getDesc_second() {
        return desc_second == null ? "" : desc_second;
    }

    public void setDesc_second(String desc_second) {
        this.desc_second = (desc_second == null ? "" : desc_second);
    }

    public String getJump_url() {
        return jump_url == null ? "" : jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = (jump_url == null ? "" : jump_url);
    }

    public ButtonBean getButton() {
        return button;
    }

    public void setButton(ButtonBean button) {
        this.button = button;
    }

    public String getOid_str() {
        return oid_str == null ? "" : oid_str;
    }

    public void setOid_str(String oid_str) {
        this.oid_str = (oid_str == null ? "" : oid_str);
    }

    public static class ButtonBean {
        /**
         * type : 1
         * jump_style : {"text":"进入"}
         * jump_url : https://www.biligame.com/detail?id=97&sourceFrom=1005
         */

        private int type;
        private JumpStyleBean jump_style;
        private String jump_url;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public JumpStyleBean getJump_style() {
            return jump_style;
        }

        public void setJump_style(JumpStyleBean jump_style) {
            this.jump_style = jump_style;
        }

        public String getJump_url() {
            return jump_url == null ? "" : jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = (jump_url == null ? "" : jump_url);
        }

        public static class JumpStyleBean {
            /**
             * text : 进入
             */

            private String text;

            public String getText() {
                return text == null ? "" : text;
            }

            public void setText(String text) {
                this.text = (text == null ? "" : text);
            }
        }
    }
}
