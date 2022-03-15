package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/26  16:18
 * @desc :
 */
public class UserInfoModel {

    /**
     * card : {}
     * space : {"s_img":"http://i1.hdslb.com/bfs/space/768cc4fd97618cf589d23c2711a1d1a729f42235.png","l_img":"http://i1.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png"}
     * following : false
     * archive_count : 2
     * article_count : 0
     * follower : 0
     */

    private SelfInfoModel card;
    private SpaceBean space;
    private boolean following;
    private int archive_count;
    private int article_count;
    private int follower;

    public SelfInfoModel getCard() {
        return card;
    }

    public void setCard(SelfInfoModel card) {
        this.card = card;
    }

    public SpaceBean getSpace() {
        return space;
    }

    public void setSpace(SpaceBean space) {
        this.space = space;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getArchive_count() {
        return archive_count;
    }

    public void setArchive_count(int archive_count) {
        this.archive_count = archive_count;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public static class CardBean {
    }

    public static class SpaceBean {
        /**
         * s_img : http://i1.hdslb.com/bfs/space/768cc4fd97618cf589d23c2711a1d1a729f42235.png
         * l_img : http://i1.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png
         */

        private String s_img;
        private String l_img;

        public String getS_img() {
            return s_img == null ? "" : s_img;
        }

        public void setS_img(String s_img) {
            this.s_img = (s_img == null ? "" : s_img);
        }

        public String getL_img() {
            return l_img == null ? "" : l_img;
        }

        public void setL_img(String l_img) {
            this.l_img = (l_img == null ? "" : l_img);
        }
    }
}
