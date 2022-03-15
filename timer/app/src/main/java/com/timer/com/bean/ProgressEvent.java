package com.timer.com.bean;

/**
 * @author : qiuyiyang
 * @date : 2022/2/12  0:43
 * @desc :
 */
public class ProgressEvent {
    public ProgressEvent(int progress) {
        this.progress=progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private int progress;
}
