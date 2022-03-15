package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/15  14:15
 * @desc :
 */
public class LoginModel {

    /**
     * status : 0
     * message :
     * url : https://passport.biligame.com/crossDomain?DedeUserID=24526478&DedeUserID__ckMd5=182601117c17adc7&Expires=1641870843&SESSDATA=c42fd727,1641871843,1fb79*71&bili_jct=904ed6aef92d3903e4cc55915469bebd&gourl=https%3A%2F%2Fwww.bilibili.com%2F
     */

    private int status;
    private String message;
    private String url;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = (message == null ? "" : message);
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = (url == null ? "" : url);
    }
}
