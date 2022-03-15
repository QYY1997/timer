package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:22
 * @desc :
 */
public class LevelModel{
    /**
     * current_level : 6
     * current_min : 0
     * current_exp : 0
     * next_exp : 0
     */

    private int current_level;//等级
    private int current_min;
    private int current_exp;
    private int next_exp;

    public int getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }

    public int getCurrent_min() {
        return current_min;
    }

    public void setCurrent_min(int current_min) {
        this.current_min = current_min;
    }

    public int getCurrent_exp() {
        return current_exp;
    }

    public void setCurrent_exp(int current_exp) {
        this.current_exp = current_exp;
    }

    public int getNext_exp() {
        return next_exp;
    }

    public void setNext_exp(int next_exp) {
        this.next_exp = next_exp;
    }
}