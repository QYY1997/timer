package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:11
 * @desc :动态列表
 */
public class DynamicListModel {

    /**
     * new_num : 0
     * exist_gap : 1
     * update_num : 0
     * open_rcmd : 1
     * cards : []
     * attentions : {}
     * max_dynamic_id : 545275862352241716
     * history_offset : 545058386690592660
     * _gt_ : 0
     */

    private int new_num;
    private int exist_gap;
    private int update_num;
    private int open_rcmd;
    private AttentionsModel attentions;
    private List<FoldBean> fold_mgr;
    private String max_dynamic_id;
    private String history_offset;
    private int _gt_;
    private int has_more;
    private String next_offset;
    private List<CardModel> cards;

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    public String getNext_offset() {
        return next_offset == null ? "" : next_offset;
    }

    public void setNext_offset(String next_offset) {
        this.next_offset = (next_offset == null ? "" : next_offset);
    }

    public List<FoldBean> getFold_mgr() {
        if (fold_mgr == null) {
            return new ArrayList<>();
        }
        return fold_mgr;
    }

    public void setFold_mgr(List<FoldBean> fold_mgr) {
        this.fold_mgr = fold_mgr;
    }

    public int getNew_num() {
        return new_num;
    }

    public void setNew_num(int new_num) {
        this.new_num = new_num;
    }

    public int getExist_gap() {
        return exist_gap;
    }

    public void setExist_gap(int exist_gap) {
        this.exist_gap = exist_gap;
    }

    public int getUpdate_num() {
        return update_num;
    }

    public void setUpdate_num(int update_num) {
        this.update_num = update_num;
    }

    public int getOpen_rcmd() {
        return open_rcmd;
    }

    public void setOpen_rcmd(int open_rcmd) {
        this.open_rcmd = open_rcmd;
    }

    public AttentionsModel getAttentions() {
        return attentions;
    }

    public void setAttentions(AttentionsModel attentions) {
        this.attentions = attentions;
    }

    public String getMax_dynamic_id() {
        return max_dynamic_id == null ? "" : max_dynamic_id;
    }

    public void setMax_dynamic_id(String max_dynamic_id) {
        this.max_dynamic_id = (max_dynamic_id == null ? "" : max_dynamic_id);
    }

    public String getHistory_offset() {
        return history_offset == null ? "" : history_offset;
    }

    public void setHistory_offset(String history_offset) {
        this.history_offset = (history_offset == null ? "" : history_offset);
    }

    public int get_gt_() {
        return _gt_;
    }

    public void set_gt_(int _gt_) {
        this._gt_ = _gt_;
    }

    public List<CardModel> getCards() {
        if (cards == null) {
            return new ArrayList<>();
        }
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }

    public static class FoldBean {

        private int fold_type;
        private List<DynamicBean> folds;

        public int getFold_type() {
            return fold_type;
        }

        public void setFold_type(int fold_type) {
            this.fold_type = fold_type;
        }

        public List<DynamicBean> getFolds() {
            if (folds == null) {
                return new ArrayList<>();
            }
            return folds;
        }

        public void setFolds(List<DynamicBean> folds) {
            this.folds = folds;
        }
    }

    public static class DynamicBean {

        private List<String> dynamic_ids;

        public List<String> getDynamic_ids() {
            if (dynamic_ids == null) {
                return new ArrayList<>();
            }
            return dynamic_ids;
        }

        public void setDynamic_ids(List<String> dynamic_ids) {
            this.dynamic_ids = dynamic_ids;
        }
    }
}
