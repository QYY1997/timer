package com.bookkeeping.myapplication.model;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/12
 */
public interface SuccessCallback<T> {
    void success(T value);
}
