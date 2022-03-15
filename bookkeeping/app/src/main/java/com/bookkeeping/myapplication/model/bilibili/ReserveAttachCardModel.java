package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/23  14:21
 * @desc :
 */
public class ReserveAttachCardModel {

    /**
     * type : reserve
     * title : 直播预约：快来和缘结神结缘！
     * state : 0
     * reserve_total : 182
     * desc_first : {"text":"明天 20:30 直播","style":0}
     * desc_second : 182人预约
     * oid_str : 64561
     * reserve_button : {"type":2,"uncheck":{"icon":"https://i0.hdslb.com/bfs/album/1d6af68e116985828780dd843ef435ccd6307e63.png","text":"预约"},"check":{"icon":"","text":"已预约","share":{"show":1,"icon":"https://i0.hdslb.com/bfs/album/466b93bea49193b2ca71ce8bc52fdc8db933c335.png","text":"分享"}},"status":1}
     * origin_state : 100
     * stype : 2
     * livePlanStartTime : 1627129800
     * up_mid : 11313303
     * user_info : {"name":"林唯艺","face":"https://i0.hdslb.com/bfs/face/30aa36c9f06de9a2aab091cec8891d251ebd749b.jpg"}
     */

    private String type;
    private String title;
    private int state;
    private int reserve_total;
    private DescFirstBean desc_first;
    private String desc_second;
    private String oid_str;
    private ReserveButtonBean reserve_button;
    private int origin_state;
    private int stype;
    private long livePlanStartTime;
    private String up_mid;
    private UserInfoBean user_info;
    private ReserveLotteryBean reserve_lottery;

    public ReserveLotteryBean getReserve_lottery() {
        return reserve_lottery;
    }

    public void setReserve_lottery(ReserveLotteryBean reserve_lottery) {
        this.reserve_lottery = reserve_lottery;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null ? "" : title);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getReserve_total() {
        return reserve_total;
    }

    public void setReserve_total(int reserve_total) {
        this.reserve_total = reserve_total;
    }

    public DescFirstBean getDesc_first() {
        return desc_first;
    }

    public void setDesc_first(DescFirstBean desc_first) {
        this.desc_first = desc_first;
    }

    public String getDesc_second() {
        return desc_second == null ? "" : desc_second;
    }

    public void setDesc_second(String desc_second) {
        this.desc_second = (desc_second == null ? "" : desc_second);
    }

    public String getOid_str() {
        return oid_str == null ? "" : oid_str;
    }

    public void setOid_str(String oid_str) {
        this.oid_str = (oid_str == null ? "" : oid_str);
    }

    public ReserveButtonBean getReserve_button() {
        return reserve_button;
    }

    public void setReserve_button(ReserveButtonBean reserve_button) {
        this.reserve_button = reserve_button;
    }

    public int getOrigin_state() {
        return origin_state;
    }

    public void setOrigin_state(int origin_state) {
        this.origin_state = origin_state;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public long getLivePlanStartTime() {
        return livePlanStartTime;
    }

    public void setLivePlanStartTime(long livePlanStartTime) {
        this.livePlanStartTime = livePlanStartTime;
    }

    public String getUp_mid() {
        return up_mid == null ? "" : up_mid;
    }

    public void setUp_mid(String up_mid) {
        this.up_mid = (up_mid == null ? "" : up_mid);
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class DescFirstBean {
        /**
         * text : 明天 20:30 直播
         * style : 0
         */

        private String text;
        private int style;

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = (text == null ? "" : text);
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }
    }

    public static class ReserveButtonBean {
        /**
         * type : 2
         * uncheck : {"icon":"https://i0.hdslb.com/bfs/album/1d6af68e116985828780dd843ef435ccd6307e63.png","text":"预约"}
         * check : {"icon":"","text":"已预约","share":{"show":1,"icon":"https://i0.hdslb.com/bfs/album/466b93bea49193b2ca71ce8bc52fdc8db933c335.png","text":"分享"}}
         * status : 1 未预约 2：已预约
         */

        private int type;
        private UncheckBean uncheck;
        private CheckBean check;
        private int status;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UncheckBean getUncheck() {
            return uncheck;
        }

        public void setUncheck(UncheckBean uncheck) {
            this.uncheck = uncheck;
        }

        public CheckBean getCheck() {
            return check;
        }

        public void setCheck(CheckBean check) {
            this.check = check;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class UncheckBean {
            /**
             * icon : https://i0.hdslb.com/bfs/album/1d6af68e116985828780dd843ef435ccd6307e63.png
             * text : 预约
             */

            private String icon;
            private String text;

            public String getIcon() {
                return icon == null ? "" : icon;
            }

            public void setIcon(String icon) {
                this.icon = (icon == null ? "" : icon);
            }

            public String getText() {
                return text == null ? "" : text;
            }

            public void setText(String text) {
                this.text = (text == null ? "" : text);
            }
        }

        public static class CheckBean {
            /**
             * icon :
             * text : 已预约
             * share : {"show":1,"icon":"https://i0.hdslb.com/bfs/album/466b93bea49193b2ca71ce8bc52fdc8db933c335.png","text":"分享"}
             */

            private String icon;
            private String text;
            private ShareBean share;

            public String getIcon() {
                return icon == null ? "" : icon;
            }

            public void setIcon(String icon) {
                this.icon = (icon == null ? "" : icon);
            }

            public String getText() {
                return text == null ? "" : text;
            }

            public void setText(String text) {
                this.text = (text == null ? "" : text);
            }

            public ShareBean getShare() {
                return share;
            }

            public void setShare(ShareBean share) {
                this.share = share;
            }

            public static class ShareBean {
                /**
                 * show : 1
                 * icon : https://i0.hdslb.com/bfs/album/466b93bea49193b2ca71ce8bc52fdc8db933c335.png
                 * text : 分享
                 */

                private int show;
                private String icon;
                private String text;

                public int getShow() {
                    return show;
                }

                public void setShow(int show) {
                    this.show = show;
                }

                public String getIcon() {
                    return icon == null ? "" : icon;
                }

                public void setIcon(String icon) {
                    this.icon = (icon == null ? "" : icon);
                }

                public String getText() {
                    return text == null ? "" : text;
                }

                public void setText(String text) {
                    this.text = (text == null ? "" : text);
                }
            }
        }
    }

    public static class ReserveLotteryBean{

        /**
         * icon : https://i0.hdslb.com/bfs/album/0c62d6a31f560c3942a787ab5220b048458ab397.png
         * text : 预约有奖：66元红包
         * jump_url : https://www.bilibili.com/h5/lottery/result?business_id=69898&business_type=10&lottery_id=67118
         * lottery_type : 1
         */

        private String icon;
        private String text;
        private String jump_url;
        private int lottery_type;

        public String getIcon() {
            return icon == null ? "" : icon;
        }

        public void setIcon(String icon) {
            this.icon = (icon == null ? "" : icon);
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = (text == null ? "" : text);
        }

        public String getJump_url() {
            return jump_url == null ? "" : jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = (jump_url == null ? "" : jump_url);
        }

        public int getLottery_type() {
            return lottery_type;
        }

        public void setLottery_type(int lottery_type) {
            this.lottery_type = lottery_type;
        }
    }
    public static class UserInfoBean {
        /**
         * name : 林唯艺
         * face : https://i0.hdslb.com/bfs/face/30aa36c9f06de9a2aab091cec8891d251ebd749b.jpg
         */

        private String name;
        private String face;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = (name == null ? "" : name);
        }

        public String getFace() {
            return face == null ? "" : face;
        }

        public void setFace(String face) {
            this.face = (face == null ? "" : face);
        }
    }
}
