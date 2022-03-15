package com.timer.com.bean;
import java.io.Serializable;

/**
 * author : qiuyiyang
 * date   : 2020/7/24 10:50
 * desc   :
 */
public class HintModel implements Serializable {

    private String content;
    private String title;
    private boolean num=true;

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNum() {
        return num;
    }

    public void setNum(boolean num) {
        this.num = num;
    }
}
