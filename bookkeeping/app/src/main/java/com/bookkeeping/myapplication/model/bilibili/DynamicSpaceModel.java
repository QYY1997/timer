package com.bookkeeping.myapplication.model.bilibili;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/26  16:32
 * @desc :
 */
public class DynamicSpaceModel {

    /**
     * items : [{"uid":24526478,"num":7}]
     * _gt_ : 0
     */

    private int _gt_;
    private List<ItemsBean> items;

    public int get_gt_() {
        return _gt_;
    }

    public void set_gt_(int _gt_) {
        this._gt_ = _gt_;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * uid : 24526478
         * num : 7
         */

        private int uid;
        private int num;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
