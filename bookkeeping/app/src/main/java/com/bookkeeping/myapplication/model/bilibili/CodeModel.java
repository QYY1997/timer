package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2022/3/22  3:13
 * @desc :
 */
public class CodeModel {

    /**
     * result : {"success":1,"gt":"d712df3d362b20bd5b3d290adf7603bc","challenge":"5966283098ce7ec15673d255e62a2001","key":"9826971944fa48bb8d7056d96e93eff8"}
     * type : 1
     */

    private ResultBean result;
    private int type;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class ResultBean {
        /**
         * success : 1
         * gt : d712df3d362b20bd5b3d290adf7603bc
         * challenge : 5966283098ce7ec15673d255e62a2001
         * key : 9826971944fa48bb8d7056d96e93eff8
         */

        private int success;
        private String gt;
        private String challenge;
        private String key;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getGt() {
            return gt == null ? "" : gt;
        }

        public void setGt(String gt) {
            this.gt = (gt == null ? "" : gt);
        }

        public String getChallenge() {
            return challenge == null ? "" : challenge;
        }

        public void setChallenge(String challenge) {
            this.challenge = (challenge == null ? "" : challenge);
        }

        public String getKey() {
            return key == null ? "" : key;
        }

        public void setKey(String key) {
            this.key = (key == null ? "" : key);
        }
    }
}
