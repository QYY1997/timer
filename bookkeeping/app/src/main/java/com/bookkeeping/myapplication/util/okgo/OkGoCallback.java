package com.bookkeeping.myapplication.util.okgo;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/3/29
 */
public interface OkGoCallback<T> {
    /**成功返回
     * @param success
     */
    void success(T success);

    /**
     * 错误
     */
    void fail();
}
