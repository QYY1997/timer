package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/15  15:59
 * @desc :
 */
public class GT3ResultModel {

    /**
     * geetest_challenge : e4a2ab3339d822b46c0e4d2f2025db61f7
     * geetest_seccode : 7a395c2f18e371933e3016654fb903c3|jordan
     * geetest_validate : 7a395c2f18e371933e3016654fb903c3
     */

    private String geetest_challenge;
    private String geetest_seccode;
    private String geetest_validate;

    public String getGeetest_challenge() {
        return geetest_challenge == null ? "" : geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = (geetest_challenge == null ? "" : geetest_challenge);
    }

    public String getGeetest_seccode() {
        return geetest_seccode == null ? "" : geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = (geetest_seccode == null ? "" : geetest_seccode);
    }

    public String getGeetest_validate() {
        return geetest_validate == null ? "" : geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = (geetest_validate == null ? "" : geetest_validate);
    }
}
