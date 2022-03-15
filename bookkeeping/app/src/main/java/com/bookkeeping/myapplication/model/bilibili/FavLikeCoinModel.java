package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/6/10  14:45
 * @desc :
 */
public class FavLikeCoinModel {

    /**
     * attention : true
     * favorite : true
     * season_fav : false
     * like : true
     * dislike : false
     * coin : 2
     */

    private boolean attention;
    private boolean favorite;
    private boolean season_fav;
    private boolean like;
    private boolean dislike;
    private int coin;

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isSeason_fav() {
        return season_fav;
    }

    public void setSeason_fav(boolean season_fav) {
        this.season_fav = season_fav;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
