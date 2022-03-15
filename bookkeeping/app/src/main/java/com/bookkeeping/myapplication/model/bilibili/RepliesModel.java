package com.bookkeeping.myapplication.model.bilibili;

import com.bookkeeping.myapplication.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  16:13
 * @desc :
 */
public class RepliesModel {

    /**
     * rpid : 4318509669
     * oid : 802307846
     * type : 1
     * mid : 285499073
     * root : 0
     * parent : 0
     * dialog : 0
     * count : 117
     * rcount : 116
     * state : 0
     * fansgrade : 0
     * attr : 0
     * ctime : 1616741854
     * rpid_str : 4318509669
     * root_str : 0
     * parent_str : 0
     * like : 2694
     * action : 0
     * member : {}
     * content : {}
     * replies : [{},{},{}]
     * assist : 0
     * folder : {}
     * up_action : {"like":false,"reply":false}
     * show_follow : false
     * invisible : false
     */

    private String rpid;
    private String oid;
    private int type;
    private String mid;
    private String root;
    private String parent;
    private String dialog;
    private int count;
    private int rcount;
    private int state;
    private int fansgrade;
    private int attr;
    private long ctime;
    private String rpid_str;
    private String root_str;
    private String parent_str;
    private int like;
    private int action;
    private MemberModel member;
    private ContentModel content;
    private int assist;
    private FolderModel folder;
    private UpActionModel up_action;
    private boolean show_follow;
    private boolean invisible;
    private List<RepliesModel> replies;

    private List<card> card_label;

    public List<card> getCard_label() {
        if (card_label == null) {
            return new ArrayList<>();
        }
        return card_label;
    }

    public void setCard_label(List<card> card_label) {
        this.card_label = card_label;
    }

    public String getRpid() {
        return rpid == null ? "" : rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid == null ? "" : rpid;
    }

    public String getOid() {
        return oid == null ? "" : oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? "" : oid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMid() {
        return mid == null ? "" : mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? "" : mid;
    }

    public String getRoot() {
        return root == null ? "" : root;
    }

    public void setRoot(String root) {
        this.root = root == null ? "" : root;
    }

    public String getParent() {
        return parent == null ? "" : parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? "" : parent;
    }

    public String getDialog() {
        return dialog == null ? "" : dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog == null ? "" : dialog;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFansgrade() {
        return fansgrade;
    }

    public void setFansgrade(int fansgrade) {
        this.fansgrade = fansgrade;
    }

    public int getAttr() {
        return attr;
    }

    public void setAttr(int attr) {
        this.attr = attr;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getRpid_str() {
        return rpid_str == null ? "" : rpid_str;
    }

    public void setRpid_str(String rpid_str) {
        this.rpid_str = rpid_str;
    }

    public String getRoot_str() {
        return root_str == null ? "" : root_str;
    }

    public void setRoot_str(String root_str) {
        this.root_str = root_str;
    }

    public String getParent_str() {
        return parent_str == null ? "" : parent_str;
    }

    public void setParent_str(String parent_str) {
        this.parent_str = parent_str;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public MemberModel getMember() {
        return member==null?new MemberModel():member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public ContentModel getContent() {
        return content==null?new ContentModel() :content;
    }

    public void setContent(ContentModel content) {
        this.content = content;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public FolderModel getFolder() {
        return folder==null?new FolderModel():folder;
    }

    public void setFolder(FolderModel folder) {
        this.folder = folder;
    }

    public UpActionModel getUp_action() {
        return up_action==null?new UpActionModel():up_action;
    }

    public void setUp_action(UpActionModel up_action) {
        this.up_action = up_action;
    }

    public boolean isShow_follow() {
        return show_follow;
    }

    public void setShow_follow(boolean show_follow) {
        this.show_follow = show_follow;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public List<RepliesModel> getReplies() {
        if (replies == null) {
            return new ArrayList<>();
        }
        return replies;
    }

    public void setReplies(List<RepliesModel> replies) {
        this.replies = replies;
    }

    public static class card {
        /**
         * rpid : 4806827129
         * text_content : UP主觉得很赞
         * text_color_day : #757575
         * text_color_night : #939393
         * label_color_day : #F4F4F4
         * label_color_night : #1E1E1E
         * image :
         */

        private String rpid;
        private String text_content;
        private String text_color_day;
        private String text_color_night;
        private String label_color_day;
        private String label_color_night;
        private String image;

        public String getRpid() {
            return rpid == null ? "" : rpid;
        }

        public void setRpid(String rpid) {
            this.rpid = (rpid == null ? "" : rpid);
        }

        public String getText_content() {
            return text_content;
        }

        public void setText_content(String text_content) {
            this.text_content = text_content;
        }

        public String getText_color_day() {
            return text_color_day;
        }

        public void setText_color_day(String text_color_day) {
            this.text_color_day = text_color_day;
        }

        public String getText_color_night() {
            return text_color_night;
        }

        public void setText_color_night(String text_color_night) {
            this.text_color_night = text_color_night;
        }

        public String getLabel_color_day() {
            return label_color_day;
        }

        public void setLabel_color_day(String label_color_day) {
            this.label_color_day = label_color_day;
        }

        public String getLabel_color_night() {
            return label_color_night;
        }

        public void setLabel_color_night(String label_color_night) {
            this.label_color_night = label_color_night;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
