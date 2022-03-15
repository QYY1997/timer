package com.timer.com.bean;

/**
 * @author : qiuyiyang
 * @date : 2021/8/27  10:34
 * @desc :
 */
public class ResultModel {

    /**
     * data : {"data":{"data":"eyJraWQiOiI5YThlNjIzYy1lZDg5LTQ2MzEtODE5NS1lM2IwNTY4NTgyNGIiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpc3N1ZXIiLCJhdWQiOiJhdWRpZW5jZSIsImV4cCI6MTY0NDc3MjQ0OSwianRpIjoiN2hMc1NMSjByeVN6SVk4VDNoanJGUSIsImlhdCI6MTY0NDY4OTY0OSwibmJmIjoxNjQ0Njg5NTI5LCJzdWIiOiJwenJ0eSIsImF0dHJpYnV0ZXMiOiJ7XCIkZXhwaXJhdGlvbi10aW1lXCI6MTY0NDc3MjQ0OTgxMX0ifQ.Yi-BOoKAx95XpdWwsaNzLrkejbqrgJnaqHyABoeNvyCm0ivWtXgVdbOPb3jdCzJKQNYlXN7hLj97MVasJwYpjdyy0d6AaEUQA5YasM0MoZ1jUK8WP_jQe9gGGYYx5I17-EPa5t2wt6ghREeg86BSyzvkS_p-Zz2Cz0wtPutWDK29ZQjE30fyqtlZixV58AA8lUwnDcW8z5G5FUWcVe83hKWLLifkV6rbIDNq2bN2CP4ZLl_-tEt0gxg08VO4qCBn3SM6N4TJR42m8dK54yBL3k_0sEmP5FHEjFPySF9GrXrG5wqSUTBX_NZsALEX08pyKZebBrfVtjxVxyFm7rOrzQ","userId":"5f733f23e4b062021e213330"},"message":"成功","retCode":0,"timeStamp":1644689649813}
     * status : true
     */

    private DataBean data;
    private boolean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * data : {"data":"eyJraWQiOiI5YThlNjIzYy1lZDg5LTQ2MzEtODE5NS1lM2IwNTY4NTgyNGIiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpc3N1ZXIiLCJhdWQiOiJhdWRpZW5jZSIsImV4cCI6MTY0NDc3MjQ0OSwianRpIjoiN2hMc1NMSjByeVN6SVk4VDNoanJGUSIsImlhdCI6MTY0NDY4OTY0OSwibmJmIjoxNjQ0Njg5NTI5LCJzdWIiOiJwenJ0eSIsImF0dHJpYnV0ZXMiOiJ7XCIkZXhwaXJhdGlvbi10aW1lXCI6MTY0NDc3MjQ0OTgxMX0ifQ.Yi-BOoKAx95XpdWwsaNzLrkejbqrgJnaqHyABoeNvyCm0ivWtXgVdbOPb3jdCzJKQNYlXN7hLj97MVasJwYpjdyy0d6AaEUQA5YasM0MoZ1jUK8WP_jQe9gGGYYx5I17-EPa5t2wt6ghREeg86BSyzvkS_p-Zz2Cz0wtPutWDK29ZQjE30fyqtlZixV58AA8lUwnDcW8z5G5FUWcVe83hKWLLifkV6rbIDNq2bN2CP4ZLl_-tEt0gxg08VO4qCBn3SM6N4TJR42m8dK54yBL3k_0sEmP5FHEjFPySF9GrXrG5wqSUTBX_NZsALEX08pyKZebBrfVtjxVxyFm7rOrzQ","userId":"5f733f23e4b062021e213330"}
         * message : 成功
         * retCode : 0
         * timeStamp : 1644689649813
         */

        private String data;
        private String message;
        private int retCode;
        private long timeStamp;

        public String getData() {
            return data == null ? "" : data;
        }

        public void setData(String data) {
            this.data = (data == null ? "" : data);
        }

        public String getMessage() {
            return message == null ? "" : message;
        }

        public void setMessage(String message) {
            this.message = (message == null ? "" : message);
        }

        public int getRetCode() {
            return retCode;
        }

        public void setRetCode(int retCode) {
            this.retCode = retCode;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
