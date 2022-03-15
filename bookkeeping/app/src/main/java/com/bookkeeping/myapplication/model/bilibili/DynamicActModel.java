package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/8/10  14:14
 * @desc :
 */
public class DynamicActModel {

    /**
     * rid : 557184900524933121
     * user : {"uid":63231,"uname":"泛式","face":"https://i0.hdslb.com/bfs/face/5f60d345059b82f0878984d9f9133f45b33b82be.jpg"}
     * vest : {"uid":63231,"content":"今年的mad大赛来啦！很好康的，很好康的，而且每天都有新的，大家一定要看哦[星星眼][星星眼] 不要逼我蹲下来求你们[大哭][大哭]","ctrl":"[]"}
     * sketch : {"title":"2021冬夏合战MAD大赛","desc_text":"最强MAD集结 决战MAD之巅！","cover_url":"https://i0.hdslb.com/bfs/activity-plat/static/20210730/a08928aa3f1a6d7fca6d869175739bfb/UsDKPdKzFP.jpg","target_url":"https://www.bilibili.com/blackboard/activity-winterVSSummer1st-m.html?from_spmid=traffic.area-rec.0.0&from_module=animation&share_source=bili&share_medium=iphone&bbid=E03E6D54-E8FE-44D5-B476-D965F6EC617A167628infoc&ts=1628481497805&native.theme=1&night=0","sketch_id":557184900524933120,"biz_type":3}
     */

    private String rid;
    private UserModel user;
    private VestBean vest;
    private SketchBean sketch;

    public String getRid() {
        return rid == null ? "" : rid;
    }

    public void setRid(String rid) {
        this.rid = (rid == null ? "" : rid);
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public VestBean getVest() {
        return vest;
    }

    public void setVest(VestBean vest) {
        this.vest = vest;
    }

    public SketchBean getSketch() {
        return sketch;
    }

    public void setSketch(SketchBean sketch) {
        this.sketch = sketch;
    }

    public static class VestBean {
        /**
         * uid : 63231
         * content : 今年的mad大赛来啦！很好康的，很好康的，而且每天都有新的，大家一定要看哦[星星眼][星星眼] 不要逼我蹲下来求你们[大哭][大哭]
         * ctrl : []
         */

        private int uid;
        private String content;
        private String ctrl;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCtrl() {
            return ctrl;
        }

        public void setCtrl(String ctrl) {
            this.ctrl = ctrl;
        }
    }

    public static class SketchBean {
        /**
         * title : 2021冬夏合战MAD大赛
         * desc_text : 最强MAD集结 决战MAD之巅！
         * cover_url : https://i0.hdslb.com/bfs/activity-plat/static/20210730/a08928aa3f1a6d7fca6d869175739bfb/UsDKPdKzFP.jpg
         * target_url : https://www.bilibili.com/blackboard/activity-winterVSSummer1st-m.html?from_spmid=traffic.area-rec.0.0&from_module=animation&share_source=bili&share_medium=iphone&bbid=E03E6D54-E8FE-44D5-B476-D965F6EC617A167628infoc&ts=1628481497805&native.theme=1&night=0
         * sketch_id : 557184900524933120
         * biz_type : 3
         */

        private String title;
        private String desc_text;
        private String cover_url;
        private String target_url;
        private long sketch_id;
        private int biz_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc_text() {
            return desc_text;
        }

        public void setDesc_text(String desc_text) {
            this.desc_text = desc_text;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getTarget_url() {
            return target_url;
        }

        public void setTarget_url(String target_url) {
            this.target_url = target_url;
        }

        public long getSketch_id() {
            return sketch_id;
        }

        public void setSketch_id(long sketch_id) {
            this.sketch_id = sketch_id;
        }

        public int getBiz_type() {
            return biz_type;
        }

        public void setBiz_type(int biz_type) {
            this.biz_type = biz_type;
        }
    }
}
