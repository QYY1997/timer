package com.timer.com.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/14  3:58
 * @desc :
 */
public class SignUpModel {

    private List<EnRollModel> list;

    public List<EnRollModel> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<EnRollModel> list) {
        this.list = list;
    }
}
