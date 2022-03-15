package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/8/11  17:54
 * @desc :
 */
public class PersonalListModel {

    /**
     * list : []
     * re_version : 0
     * total : 87
     */

    private int re_version;
    private int total;
    private List<PersonalModel> list;

    public int getRe_version() {
        return re_version;
    }

    public void setRe_version(int re_version) {
        this.re_version = re_version;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PersonalModel> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<PersonalModel> list) {
        this.list = list;
    }
}
