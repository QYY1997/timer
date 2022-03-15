package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/26  16:25
 * @desc :
 */
public class AttentionsModel {

    private List<String> uids;
    private List<BangumisBean> bangumis;

    public List<String> getUids() {
        if (uids == null) {
            return new ArrayList<>();
        }
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public List<BangumisBean> getBangumis() {
        if (bangumis == null) {
            return new ArrayList<>();
        }
        return bangumis;
    }

    public void setBangumis(List<BangumisBean> bangumis) {
        this.bangumis = bangumis;
    }

    public static class BangumisBean {

        private String season_id;
        private int type;

        public String getSeason_id() {
            return season_id == null ? "" : season_id;
        }

        public void setSeason_id(String season_id) {
            this.season_id = (season_id == null ? "" : season_id);
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}