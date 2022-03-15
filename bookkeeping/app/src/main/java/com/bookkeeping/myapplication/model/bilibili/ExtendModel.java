package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  13:52
 * @desc :
 */
public class ExtendModel {

    /**
     * s : {"ogv":{"ogv_id":0}}
     * dispute : {"content":"温馨提示：过量饮酒，有害健康"}
     * from : {"from":"","verify":{}}
     * like_icon : {"action":"","action_url":"","end":"","end_url":"","start":"","start_url":""}
     * repeat_resource : {"items":[{"rid":589458865,"type":4310}]}
     * topic : {"is_attach_topic":1}
     */

    private SBean s;
    private DisputeBean dispute;
    private FromBean from;
    private LikeIconBean like_icon;
    private RepeatResourceBean repeat_resource;
    private TopicBean topic;
    private List<CtrlBean> ctrl;
    private LbsBean lbs;



    public List<CtrlBean> getCtrl() {
        if (ctrl == null) {
            return new ArrayList<>();
        }
        return ctrl;
    }

    public void setCtrl(List<CtrlBean> ctrl) {
        this.ctrl = ctrl;
    }

    public SBean getS() {
        return s;
    }

    public void setS(SBean s) {
        this.s = s;
    }

    public DisputeBean getDispute() {
        return dispute;
    }

    public void setDispute(DisputeBean dispute) {
        this.dispute = dispute;
    }

    public FromBean getFrom() {
        return from;
    }

    public void setFrom(FromBean from) {
        this.from = from;
    }

    public LikeIconBean getLike_icon() {
        return like_icon;
    }

    public void setLike_icon(LikeIconBean like_icon) {
        this.like_icon = like_icon;
    }

    public RepeatResourceBean getRepeat_resource() {
        return repeat_resource;
    }

    public void setRepeat_resource(RepeatResourceBean repeat_resource) {
        this.repeat_resource = repeat_resource;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }

    public static class CtrlBean{

        /**
         * data : 8047632
         * length : 8
         * location : 8
         * type : 1
         */

        private String data;
        private int length;
        private int location;
        private int type;

        public String getData() {
            return data == null ? "" : data;
        }

        public void setData(String data) {
            this.data = (data == null ? "" : data);
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
    public static class SBean {
        /**
         * ogv : {"ogv_id":0}
         */

        private OgvBean ogv;

        public OgvBean getOgv() {
            return ogv;
        }

        public void setOgv(OgvBean ogv) {
            this.ogv = ogv;
        }

        public static class OgvBean {
            /**
             * ogv_id : 0
             */

            private int ogv_id;

            public int getOgv_id() {
                return ogv_id;
            }

            public void setOgv_id(int ogv_id) {
                this.ogv_id = ogv_id;
            }
        }
    }

    public static class DisputeBean {
        /**
         * content : 温馨提示：过量饮酒，有害健康
         */

        private String content;

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = (content == null ? "" : content);
        }
    }

    public static class FromBean {
        /**
         * from :
         * verify : {}
         */

        private String from;
        private VerifyBean verify;

        public String getFrom() {
            return from == null ? "" : from;
        }

        public void setFrom(String from) {
            this.from = (from == null ? "" : from);
        }

        public VerifyBean getVerify() {
            return verify;
        }

        public void setVerify(VerifyBean verify) {
            this.verify = verify;
        }

        public static class VerifyBean {

            /**
             * asw : {}
             * cc : {"nv":1}
             * csw : {}
             * dc : {}
             * ra : {}
             * sp : {}
             * sw : {}
             * ur : {}
             */

            private AswBean asw;
            private CcBean cc;
            private CswBean csw;
            private DcBean dc;
            private RaBean ra;
            private SpBean sp;
            private SwBean sw;
            private UrBean ur;

            public AswBean getAsw() {
                return asw;
            }

            public void setAsw(AswBean asw) {
                this.asw = asw;
            }

            public CcBean getCc() {
                return cc;
            }

            public void setCc(CcBean cc) {
                this.cc = cc;
            }

            public CswBean getCsw() {
                return csw;
            }

            public void setCsw(CswBean csw) {
                this.csw = csw;
            }

            public DcBean getDc() {
                return dc;
            }

            public void setDc(DcBean dc) {
                this.dc = dc;
            }

            public RaBean getRa() {
                return ra;
            }

            public void setRa(RaBean ra) {
                this.ra = ra;
            }

            public SpBean getSp() {
                return sp;
            }

            public void setSp(SpBean sp) {
                this.sp = sp;
            }

            public SwBean getSw() {
                return sw;
            }

            public void setSw(SwBean sw) {
                this.sw = sw;
            }

            public UrBean getUr() {
                return ur;
            }

            public void setUr(UrBean ur) {
                this.ur = ur;
            }

            public static class AswBean {
            }

            public static class CcBean {
                /**
                 * nv : 1
                 */

                private int nv;

                public int getNv() {
                    return nv;
                }

                public void setNv(int nv) {
                    this.nv = nv;
                }
            }

            public static class CswBean {
            }

            public static class DcBean {
            }

            public static class RaBean {
            }

            public static class SpBean {
            }

            public static class SwBean {
            }

            public static class UrBean {
            }
        }
    }

    public static class LikeIconBean {
        /**
         * action :
         * action_url :
         * end :
         * end_url :
         * start :
         * start_url :
         */

        private String action;
        private String action_url;
        private String end;
        private String end_url;
        private String start;
        private String start_url;

        public String getAction() {
            return action == null ? "" : action;
        }

        public void setAction(String action) {
            this.action = (action == null ? "" : action);
        }

        public String getAction_url() {
            return action_url == null ? "" : action_url;
        }

        public void setAction_url(String action_url) {
            this.action_url = (action_url == null ? "" : action_url);
        }

        public String getEnd() {
            return end == null ? "" : end;
        }

        public void setEnd(String end) {
            this.end = (end == null ? "" : end);
        }

        public String getEnd_url() {
            return end_url == null ? "" : end_url;
        }

        public void setEnd_url(String end_url) {
            this.end_url = (end_url == null ? "" : end_url);
        }

        public String getStart() {
            return start == null ? "" : start;
        }

        public void setStart(String start) {
            this.start = (start == null ? "" : start);
        }

        public String getStart_url() {
            return start_url == null ? "" : start_url;
        }

        public void setStart_url(String start_url) {
            this.start_url = (start_url == null ? "" : start_url);
        }
    }

    public static class RepeatResourceBean {
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * rid : 589458865
             * type : 4310
             */

            private int rid;
            private int type;

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }

    public static class LbsBean{

        /**
         * address : 上海市
         * distance : 0
         * location : {"lat":31.333332,"lng":121.496437}
         * poi : 156310000
         * show_title : 上海市
         * title : 上海市
         * type : 1
         */

        private String address;
        private int distance;
        private LocationBean location;
        private String poi;
        private String show_title;
        private String title;
        private int type;

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = (address == null ? "" : address);
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getPoi() {
            return poi == null ? "" : poi;
        }

        public void setPoi(String poi) {
            this.poi = (poi == null ? "" : poi);
        }

        public String getShow_title() {
            return show_title == null ? "" : show_title;
        }

        public void setShow_title(String show_title) {
            this.show_title = (show_title == null ? "" : show_title);
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = (title == null ? "" : title);
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class LocationBean {
            /**
             * lat : 31.333332
             * lng : 121.496437
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
    public static class TopicBean {
        /**
         * is_attach_topic : 1
         */

        private int is_attach_topic;

        public int getIs_attach_topic() {
            return is_attach_topic;
        }

        public void setIs_attach_topic(int is_attach_topic) {
            this.is_attach_topic = is_attach_topic;
        }
    }
}
