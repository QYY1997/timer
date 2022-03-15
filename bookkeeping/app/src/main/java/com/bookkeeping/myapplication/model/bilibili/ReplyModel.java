package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  16:11
 * @desc :
 */
public class ReplyModel {

    /**
     * page : {"num":1,"size":20,"count":2429,"acount":4772}
     * config : {"showadmin":1,"showentry":1,"showfloor":0,"showtopic":1,"show_up_flag":true,"read_only":false,"show_del_log":false}
     * replies : []
     * hots : []
     * upper : {"mid":295723,"top":null,"vote":null}
     * top : null
     * notice : null
     * vote : 0
     * blacklist : 0
     * assist : 0
     * mode : 3
     * support_mode : [1,2,3]
     * folder : {"has_folded":false,"is_folded":false,"rule":"https://www.bilibili.com/blackboard/foldingreply.html"}
     * lottery_card : null
     * show_bvid : true
     * control : {"input_disable":false,"root_input_text":"发一条友善的评论","child_input_text":"","giveup_input_text":"不发没关系，请继续友善哦~","bg_text":"看看下面~来发评论吧","web_selection":false,"answer_guide_text":"需要升级成为lv2会员后才可以评论，先去答题转正吧！","answer_guide_icon_url":"http://i0.hdslb.com/bfs/emote/96940d16602cacbbac796245b7bb99fa9b5c970c.png","answer_guide_ios_url":"https://www.bilibili.com/h5/newbie/entry?navhide=1&re_src=12","answer_guide_android_url":"https://www.bilibili.com/h5/newbie/entry?navhide=1&re_src=6"}
     */

    private PageBean page;
    private ConfigBean config;
    private UpperBean upper;
    private RepliesModel root;
    private RepliesModel top;
//    private Object notice;
    private int vote;
    private int blacklist;
    private int assist;
    private int mode;
    private FolderModel folder;
//    private Object lottery_card;
    private boolean show_bvid;
    private ControlBean control;
    private List<RepliesModel> replies;
    private List<RepliesModel> hots;
    private List<Integer> support_mode;

    public RepliesModel getTop() {
        return top!=null?top:new RepliesModel();
    }

    public void setTop(RepliesModel top) {
        this.top = top;
    }

    public RepliesModel getRoot() {
        return root!=null?root:new RepliesModel();
    }

    public void setRoot(RepliesModel root) {
        this.root = root;
    }

    public PageBean getPage() {
        return page==null?new PageBean():page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public ConfigBean getConfig() {
        return config==null?new ConfigBean():config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public UpperBean getUpper() {
        return upper==null?new UpperBean():upper;
    }

    public void setUpper(UpperBean upper) {
        this.upper = upper;
    }

//    public RepliesModel getTop() {
//        return top==null ?new RepliesModel():top;
//    }
//
//    public void setTop(RepliesModel top) {
//        this.top = top;
//    }

//    public Object getNotice() {
//        return notice;
//    }
//
//    public void setNotice(Object notice) {
//        this.notice = notice;
//    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(int blacklist) {
        this.blacklist = blacklist;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public FolderModel getFolder() {
        return folder==null?new FolderModel():folder;
    }

    public void setFolder(FolderModel folder) {
        this.folder = folder;
    }

//    public Object getLottery_card() {
//        return lottery_card;
//    }
//
//    public void setLottery_card(Object lottery_card) {
//        this.lottery_card = lottery_card;
//    }

    public boolean isShow_bvid() {
        return show_bvid;
    }

    public void setShow_bvid(boolean show_bvid) {
        this.show_bvid = show_bvid;
    }

    public ControlBean getControl() {
        return control==null?new ControlBean():control;
    }

    public void setControl(ControlBean control) {
        this.control = control;
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

    public List<RepliesModel> getHots() {
        return hots;
    }

    public void setHots(List<RepliesModel> hots) {
        this.hots = hots;
    }

    public List<Integer> getSupport_mode() {
        return support_mode;
    }

    public void setSupport_mode(List<Integer> support_mode) {
        this.support_mode = support_mode;
    }

    public static class PageBean {
        /**
         * num : 1
         * size : 20
         * count : 2429
         * acount : 4772
         */

        private int num;
        private int size;
        private int count;
        private int acount;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getAcount() {
            return acount;
        }

        public void setAcount(int acount) {
            this.acount = acount;
        }
    }

    public static class ConfigBean {
        /**
         * showadmin : 1
         * showentry : 1
         * showfloor : 0
         * showtopic : 1
         * show_up_flag : true
         * read_only : false
         * show_del_log : false
         */

        private int showadmin;
        private int showentry;
        private int showfloor;
        private int showtopic;
        private boolean show_up_flag;
        private boolean read_only;
        private boolean show_del_log;

        public int getShowadmin() {
            return showadmin;
        }

        public void setShowadmin(int showadmin) {
            this.showadmin = showadmin;
        }

        public int getShowentry() {
            return showentry;
        }

        public void setShowentry(int showentry) {
            this.showentry = showentry;
        }

        public int getShowfloor() {
            return showfloor;
        }

        public void setShowfloor(int showfloor) {
            this.showfloor = showfloor;
        }

        public int getShowtopic() {
            return showtopic;
        }

        public void setShowtopic(int showtopic) {
            this.showtopic = showtopic;
        }

        public boolean isShow_up_flag() {
            return show_up_flag;
        }

        public void setShow_up_flag(boolean show_up_flag) {
            this.show_up_flag = show_up_flag;
        }

        public boolean isRead_only() {
            return read_only;
        }

        public void setRead_only(boolean read_only) {
            this.read_only = read_only;
        }

        public boolean isShow_del_log() {
            return show_del_log;
        }

        public void setShow_del_log(boolean show_del_log) {
            this.show_del_log = show_del_log;
        }
    }

    public static class UpperBean {
        /**
         * mid : 295723
         * top : null
         * vote : null
         */

        private int mid;
        private RepliesModel top;
        private int vote;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public RepliesModel getTop() {
            return top;
        }

        public void setTop(RepliesModel top) {
            this.top = top;
        }

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
        }
    }


    public static class ControlBean {
        /**
         * input_disable : false
         * root_input_text : 发一条友善的评论
         * child_input_text :
         * giveup_input_text : 不发没关系，请继续友善哦~
         * bg_text : 看看下面~来发评论吧
         * web_selection : false
         * answer_guide_text : 需要升级成为lv2会员后才可以评论，先去答题转正吧！
         * answer_guide_icon_url : http://i0.hdslb.com/bfs/emote/96940d16602cacbbac796245b7bb99fa9b5c970c.png
         * answer_guide_ios_url : https://www.bilibili.com/h5/newbie/entry?navhide=1&re_src=12
         * answer_guide_android_url : https://www.bilibili.com/h5/newbie/entry?navhide=1&re_src=6
         */

        private boolean input_disable;
        private String root_input_text;
        private String child_input_text;
        private String giveup_input_text;
        private String bg_text;
        private boolean web_selection;
        private String answer_guide_text;
        private String answer_guide_icon_url;
        private String answer_guide_ios_url;
        private String answer_guide_android_url;

        public boolean isInput_disable() {
            return input_disable;
        }

        public void setInput_disable(boolean input_disable) {
            this.input_disable = input_disable;
        }

        public String getRoot_input_text() {
            return root_input_text == null ? "" : root_input_text;
        }

        public void setRoot_input_text(String root_input_text) {
            this.root_input_text = root_input_text;
        }

        public String getChild_input_text() {
            return child_input_text == null ? "" : child_input_text;
        }

        public void setChild_input_text(String child_input_text) {
            this.child_input_text = child_input_text;
        }

        public String getGiveup_input_text() {
            return giveup_input_text == null ? "" : giveup_input_text;
        }

        public void setGiveup_input_text(String giveup_input_text) {
            this.giveup_input_text = giveup_input_text;
        }

        public String getBg_text() {
            return bg_text == null ? "" : bg_text;
        }

        public void setBg_text(String bg_text) {
            this.bg_text = bg_text;
        }

        public boolean isWeb_selection() {
            return web_selection;
        }

        public void setWeb_selection(boolean web_selection) {
            this.web_selection = web_selection;
        }

        public String getAnswer_guide_text() {
            return answer_guide_text == null ? "" : answer_guide_text;
        }

        public void setAnswer_guide_text(String answer_guide_text) {
            this.answer_guide_text = answer_guide_text;
        }

        public String getAnswer_guide_icon_url() {
            return answer_guide_icon_url == null ? "" : answer_guide_icon_url;
        }

        public void setAnswer_guide_icon_url(String answer_guide_icon_url) {
            this.answer_guide_icon_url = answer_guide_icon_url;
        }

        public String getAnswer_guide_ios_url() {
            return answer_guide_ios_url == null ? "" : answer_guide_ios_url;
        }

        public void setAnswer_guide_ios_url(String answer_guide_ios_url) {
            this.answer_guide_ios_url = answer_guide_ios_url;
        }

        public String getAnswer_guide_android_url() {
            return answer_guide_android_url == null ? "" : answer_guide_android_url;
        }

        public void setAnswer_guide_android_url(String answer_guide_android_url) {
            this.answer_guide_android_url = answer_guide_android_url;
        }
    }
}
