package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:36
 * @desc :动态附加信息
 */
public class DisplayModel {

    /**
     * origin : {"topic_info":{"topic_details":[{"topic_id":2908447,"topic_name":"碧蓝航线","is_activity":1,"topic_link":"https://www.bilibili.com/blackboard/dynamic/63094"},{"topic_id":20152722,"topic_name":"通关bilibili游戏梦境","is_activity":1,"topic_link":"https://game.bilibili.com/2021bw"},{"topic_id":20152721,"topic_name":"通关BILIBILI游戏梦境","is_activity":0,"topic_link":""}]},"relation":{"status":1,"is_follow":0,"is_followed":0},"attach_card":{"type":"game","head_text":"相关游戏","cover_url":"https://i0.hdslb.com/bfs/game/f15afdeae1ff7d3e0a045847a5cf4741cfd7c184.png","cover_type":1,"title":"碧蓝航线","desc_first":"养成/美少女/即时海战","desc_second":"4周年版本","jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005","button":{"type":1,"jump_style":{"text":"进入"},"jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005"},"oid_str":"97"},"add_on_card_info":[{"add_on_card_show_type":2,"attach_card":{"type":"game","head_text":"相关游戏","cover_url":"https://i0.hdslb.com/bfs/game/f15afdeae1ff7d3e0a045847a5cf4741cfd7c184.png","cover_type":1,"title":"碧蓝航线","desc_first":"养成/美少女/即时海战","desc_second":"4周年版本","jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005","button":{"type":1,"jump_style":{"text":"进入"},"jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005"},"oid_str":"97"}}],"show_tip":{"del_tip":"要删除动态吗？"}}
     * emoji_info : {"emoji_details":[{"emoji_name":"[tv_可爱]","id":71,"package_id":2,"state":0,"type":1,"attr":0,"text":"[tv_可爱]","url":"https://i0.hdslb.com/bfs/emote/9e55fd9b500ac4b96613539f1ce2f9499e314ed9.png","meta":{"size":1},"mtime":1608792900}]}
     * relation : {"status":2,"is_follow":1,"is_followed":0}
     * comment_info : {"comment_ids":""}
     * topic_info : {}
     * usr_action_txt : 投稿了视频
     * tags : []
     * show_tip : {}
     * cover_play_icon_url : https://i0.hdslb.com/bfs/album/2269afa7897830b397797ebe5f032b899b405c67.png
     */
    private DisplayModel origin;
    private EmojiInfoBean emoji_info;
    private RelationModel relation;
    private CommentInfoBean comment_info;
    private TopicInfoModel topic_info;
    private String usr_action_txt;
    private ShowTipModel show_tip;
    private String cover_play_icon_url;
    private AttachCardModel attach_card;
    private List<TagModel> tags;
    private List<AddOnCardInfoBean> add_on_card_info;
    private LikeInfoBean like_info;

    public LikeInfoBean getLike_info() {
        return like_info;
    }

    public void setLike_info(LikeInfoBean like_info) {
        this.like_info = like_info;
    }

    public DisplayModel getOrigin() {
        return origin;
    }

    public void setOrigin(DisplayModel origin) {
        this.origin = origin;
    }

    public EmojiInfoBean getEmoji_info() {
        return emoji_info;
    }

    public void setEmoji_info(EmojiInfoBean emoji_info) {
        this.emoji_info = emoji_info;
    }

    public RelationModel getRelation() {
        return relation;
    }

    public void setRelation(RelationModel relation) {
        this.relation = relation;
    }

    public CommentInfoBean getComment_info() {
        return comment_info;
    }

    public void setComment_info(CommentInfoBean comment_info) {
        this.comment_info = comment_info;
    }

    public TopicInfoModel getTopic_info() {
        return topic_info;
    }

    public void setTopic_info(TopicInfoModel topic_info) {
        this.topic_info = topic_info;
    }

    public String getUsr_action_txt() {
        return usr_action_txt == null ? "" : usr_action_txt;
    }

    public void setUsr_action_txt(String usr_action_txt) {
        this.usr_action_txt = (usr_action_txt == null ? "" : usr_action_txt);
    }

    public ShowTipModel getShow_tip() {
        return show_tip;
    }

    public void setShow_tip(ShowTipModel show_tip) {
        this.show_tip = show_tip;
    }

    public String getCover_play_icon_url() {
        return cover_play_icon_url == null ? "" : cover_play_icon_url;
    }

    public void setCover_play_icon_url(String cover_play_icon_url) {
        this.cover_play_icon_url = (cover_play_icon_url == null ? "" : cover_play_icon_url);
    }

    public AttachCardModel getAttach_card() {
        return attach_card;
    }

    public void setAttach_card(AttachCardModel attach_card) {
        this.attach_card = attach_card;
    }

    public List<TagModel> getTags() {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public List<AddOnCardInfoBean> getAdd_on_card_info() {
        if (add_on_card_info == null) {
            return new ArrayList<>();
        }
        return add_on_card_info;
    }

    public void setAdd_on_card_info(List<AddOnCardInfoBean> add_on_card_info) {
        this.add_on_card_info = add_on_card_info;
    }

    public static class AddOnCardInfoBean {
        /**
         * add_on_card_show_type : 2
         * attach_card : {"type":"game","head_text":"相关游戏","cover_url":"https://i0.hdslb.com/bfs/game/f15afdeae1ff7d3e0a045847a5cf4741cfd7c184.png","cover_type":1,"title":"碧蓝航线","desc_first":"养成/美少女/即时海战","desc_second":"4周年版本","jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005","button":{"type":1,"jump_style":{"text":"进入"},"jump_url":"https://www.biligame.com/detail?id=97&sourceFrom=1005"},"oid_str":"97"}
         */

        private int add_on_card_show_type;
        private AttachCardModel attach_card;
        private ReserveAttachCardModel reserve_attach_card;

        public ReserveAttachCardModel getReserve_attach_card() {
            return reserve_attach_card;
        }

        public void setReserve_attach_card(ReserveAttachCardModel reserve_attach_card) {
            this.reserve_attach_card = reserve_attach_card;
        }

        public int getAdd_on_card_show_type() {
            return add_on_card_show_type;
        }

        public void setAdd_on_card_show_type(int add_on_card_show_type) {
            this.add_on_card_show_type = add_on_card_show_type;
        }

        public AttachCardModel getAttach_card() {
            return attach_card;
        }

        public void setAttach_card(AttachCardModel attach_card) {
            this.attach_card = attach_card;
        }
    }

    public static class EmojiInfoBean {

        private List<EmoteModel> emoji_details;

        public List<EmoteModel> getEmoji_details() {
            return emoji_details;
        }

        public void setEmoji_details(List<EmoteModel> emoji_details) {
            this.emoji_details = emoji_details;
        }
    }

    public static class LikeInfoBean {
        /**
         * display_text : 赞了
         * like_users : [{"uid":2689967,"uname":"醋醋cucu"}]
         */

        private String display_text;
        private List<LikeUsersBean> like_users;

        public String getDisplay_text() {
            return display_text;
        }

        public void setDisplay_tex(String display_text) {
            this.display_text = display_text;
        }

        public List<LikeUsersBean> getLike_users() {
            return like_users;
        }

        public void setLike_users(List<LikeUsersBean> like_users) {
            this.like_users = like_users;
        }

        public static class LikeUsersBean {
            /**
             * uid : 2689967
             * uname : 醋醋cucu
             */

            private int uid;
            private String uname;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }
        }
    }
    public static class CommentInfoBean {
        /**
         * comment_ids :
         */

        private String comment_ids;

        public String getComment_ids() {
            return comment_ids == null ? "" : comment_ids;
        }

        public void setComment_ids(String comment_ids) {
            this.comment_ids = (comment_ids == null ? "" : comment_ids);
        }
    }
}
