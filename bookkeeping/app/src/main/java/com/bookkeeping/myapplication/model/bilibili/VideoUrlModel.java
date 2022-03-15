package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/4/8  15:11
 * @desc :
 */
public class VideoUrlModel {

    /**
     * from : local
     * result : suee
     * message :
     * quality : 64
     * format : flv720
     * timelength : 130146
     * accept_format : hdflv2,hdflv2,flv,flv720,flv480,mp4
     * accept_description : ["超清 4K","高清 1080P+","高清 1080P","高清 720P","清晰 480P","流畅 360P"]
     * accept_quality : [120,112,80,64,32,16]
     * video_codecid : 7
     * seek_param : start
     * seek_type : offset
     * durl : [{"order":1,"length":130146,"size":24073644,"ahead":"","vhead":"","url":"https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=1843ff43909c4af19422ddf307876cc3&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=0,3&agrr=1&logo=80000000","backup_url":["https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=1843ff43909c4af19422ddf307876cc3&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=1,3&agrr=1&logo=40000000","https://upos-sz-mirrorkodob.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobbv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=d39b79f792b3638d4ef07b201352aca4&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=2,3&agrr=1&logo=40000000"]}]
     * support_formats : [{"quality":120,"format":"hdflv2","new_description":"4K 超清","display_desc":"4K","superscript":""},{"quality":112,"format":"hdflv2","new_description":"1080P 高码率","display_desc":"1080P","superscript":"高码率"},{"quality":80,"format":"flv","new_description":"1080P 高清","display_desc":"1080P","superscript":""},{"quality":64,"format":"flv720","new_description":"720P 高清","display_desc":"720P","superscript":""},{"quality":32,"format":"flv480","new_description":"480P 清晰","display_desc":"480P","superscript":""},{"quality":16,"format":"mp4","new_description":"360P 流畅","display_desc":"360P","superscript":""}]
     * high_format : null
     */

    private String from;
    private String result;
    private String message;
    private int quality;
    private String format;
    private int timelength;
    private String accept_format;
    private int video_codecid;
    private String seek_param;
    private String seek_type;
    private SupportFormatsBean high_format;
    private List<String> accept_description;
    private List<Integer> accept_quality;
    private List<DurlBean> durl;
    private List<SupportFormatsBean> support_formats;

    public String getFrom() {
        return from == null ? "" : from;
    }

    public void setFrom(String from) {
        this.from = from == null ? "" : from;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result == null ? "" : result;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getFormat() {
        return format == null ? "" : format;
    }

    public void setFormat(String format) {
        this.format = format == null ? "" : format;
    }

    public int getTimelength() {
        return timelength;
    }

    public void setTimelength(int timelength) {
        this.timelength = timelength;
    }

    public String getAccept_format() {
        return accept_format == null ? "" : accept_format;
    }

    public void setAccept_format(String accept_format) {
        this.accept_format = accept_format == null ? "" : accept_format;
    }

    public int getVideo_codecid() {
        return video_codecid;
    }

    public void setVideo_codecid(int video_codecid) {
        this.video_codecid = video_codecid;
    }

    public String getSeek_param() {
        return seek_param == null ? "" : seek_param;
    }

    public void setSeek_param(String seek_param) {
        this.seek_param = seek_param == null ? "" : seek_param;
    }

    public String getSeek_type() {
        return seek_type == null ? "" : seek_type;
    }

    public void setSeek_type(String seek_type) {
        this.seek_type = seek_type == null ? "" : seek_type;
    }

    public SupportFormatsBean getHigh_format() {
        return high_format==null?new SupportFormatsBean():high_format;
    }

    public void setHigh_format(SupportFormatsBean high_format) {
        this.high_format = high_format;
    }

    public List<String> getAccept_description() {
        if (accept_description == null) {
            return new ArrayList<>();
        }
        return accept_description;
    }

    public void setAccept_description(List<String> accept_description) {
        this.accept_description = accept_description;
    }

    public List<Integer> getAccept_quality() {
        if (accept_quality == null) {
            return new ArrayList<>();
        }
        return accept_quality;
    }

    public void setAccept_quality(List<Integer> accept_quality) {
        this.accept_quality = accept_quality;
    }

    public List<DurlBean> getDurl() {
        if (durl == null) {
            return new ArrayList<>();
        }
        return durl;
    }

    public void setDurl(List<DurlBean> durl) {
        this.durl = durl;
    }

    public List<SupportFormatsBean> getSupport_formats() {
        if (support_formats == null) {
            return new ArrayList<>();
        }
        return support_formats;
    }

    public void setSupport_formats(List<SupportFormatsBean> support_formats) {
        this.support_formats = support_formats;
    }

    public static class DurlBean {
        /**
         * order : 1
         * length : 130146
         * size : 24073644
         * ahead :
         * vhead :
         * url : https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=1843ff43909c4af19422ddf307876cc3&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=0,3&agrr=1&logo=80000000
         * backup_url : ["https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=1843ff43909c4af19422ddf307876cc3&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=1,3&agrr=1&logo=40000000","https://upos-sz-mirrorkodob.bilivideo.com/upgcxcode/74/73/306397374/306397374-1-64.flv?e=ig8euxZM2rNcNbKjhwdVhoMM7bdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1617873030&gen=playurlv2&os=kodobbv&oi=1699088251&trid=2737f8864c8d48a1bd7e3011250cd09au&platform=pc&upsig=d39b79f792b3638d4ef07b201352aca4&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=24526478&orderid=2,3&agrr=1&logo=40000000"]
         */

        private int order;
        private int length;
        private int size;
        private String ahead;
        private String vhead;
        private String url;
        private List<String> backup_url;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getAhead() {
            return ahead == null ? "" : ahead;
        }

        public void setAhead(String ahead) {
            this.ahead = ahead == null ? "" : ahead;
        }

        public String getVhead() {
            return vhead == null ? "" : vhead;
        }

        public void setVhead(String vhead) {
            this.vhead = vhead == null ? "" : vhead;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url == null ? "" : url;
        }

        public List<String> getBackup_url() {
            if (backup_url == null) {
                return new ArrayList<>();
            }
            return backup_url;
        }

        public void setBackup_url(List<String> backup_url) {
            this.backup_url = backup_url;
        }
    }

    public static class SupportFormatsBean {
        /**
         * quality : 120
         * format : hdflv2
         * new_description : 4K 超清
         * display_desc : 4K
         * superscript :
         */

        private int quality;
        private String format;
        private String new_description;
        private String display_desc;
        private String superscript;

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getFormat() {
            return format == null ? "" : format;
        }

        public void setFormat(String format) {
            this.format = format == null ? "" : format;
        }

        public String getNew_description() {
            return new_description == null ? "" : new_description;
        }

        public void setNew_description(String new_description) {
            this.new_description = new_description == null ? "" : new_description;
        }

        public String getDisplay_desc() {
            return display_desc == null ? "" : display_desc;
        }

        public void setDisplay_desc(String display_desc) {
            this.display_desc = display_desc == null ? "" : display_desc;
        }

        public String getSuperscript() {
            return superscript == null ? "" : superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript == null ? "" : superscript;
        }
    }
}
