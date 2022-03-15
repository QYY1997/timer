package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/14  19:47
 * @desc :
 */
public class VerificationCodeModel {

    /**
     * type : geetest
     * token : b80d0f5fcbf14d379e4d0acea313390a
     * geetest : {"challenge":"75e245354496ecabdfc064b7e46f4d36","gt":"7fa7d480550df273db851dcb2b04babf"}
     * tencent : {"appid":""}
     */

    private String type;
    private String token;
    private GeetestBean geetest;
    private TencentBean tencent;

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = (token == null ? "" : token);
    }

    public GeetestBean getGeetest() {
        return geetest;
    }

    public void setGeetest(GeetestBean geetest) {
        this.geetest = geetest;
    }

    public TencentBean getTencent() {
        return tencent;
    }

    public void setTencent(TencentBean tencent) {
        this.tencent = tencent;
    }

    public static class GeetestBean {
        /**
         * challenge : 75e245354496ecabdfc064b7e46f4d36
         * gt : 7fa7d480550df273db851dcb2b04babf
         */

        private String challenge;
        private String gt;

        public String getChallenge() {
            return challenge == null ? "" : challenge;
        }

        public void setChallenge(String challenge) {
            this.challenge = (challenge == null ? "" : challenge);
        }

        public String getGt() {
            return gt == null ? "" : gt;
        }

        public void setGt(String gt) {
            this.gt = (gt == null ? "" : gt);
        }
    }

    public static class TencentBean {
        /**
         * appid :
         */

        private String appid;

        public String getAppid() {
            return appid == null ? "" : appid;
        }

        public void setAppid(String appid) {
            this.appid = (appid == null ? "" : appid);
        }
    }
}
