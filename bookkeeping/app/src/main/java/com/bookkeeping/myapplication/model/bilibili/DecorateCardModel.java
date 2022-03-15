package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:26
 * @desc :
 */
public class DecorateCardModel {

    /**
     * mid : 3306866
     * id : 3643
     * card_url : https://i0.hdslb.com/bfs/garb/item/0f872058f24cd655c53e0b421db286c687c65289.png
     * card_type : 2
     * name : 星座系列：摩羯座
     * expire_time : 0
     * card_type_name : 免费
     * uid : 3306866
     * item_id : 3643
     * item_type : 1
     * big_card_url : https://i0.hdslb.com/bfs/garb/item/0f872058f24cd655c53e0b421db286c687c65289.png
     * jump_url : https://www.bilibili.com/h5/mall/fans/recommend/3660?navhide=1&mid=3306866&from=dynamic
     * fan : {"is_fan":0,"number":0,"color":"","num_desc":""}
     * image_enhance : https://i0.hdslb.com/bfs/garb/item/0f872058f24cd655c53e0b421db286c687c65289.png
     */

    private int mid;
    private int id;
    private String card_url;
    private int card_type;
    private String name;
    private int expire_time;
    private String card_type_name;
    private int uid;
    private int item_id;
    private int item_type;
    private String big_card_url;
    private String jump_url;
    private FanBean fan;
    private String image_enhance;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_url() {
        return card_url == null ? "" : card_url;
    }

    public void setCard_url(String card_url) {
        this.card_url = (card_url == null ? "" : card_url);
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = (name == null ? "" : name);
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public String getCard_type_name() {
        return card_type_name == null ? "" : card_type_name;
    }

    public void setCard_type_name(String card_type_name) {
        this.card_type_name = (card_type_name == null ? "" : card_type_name);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getBig_card_url() {
        return big_card_url == null ? "" : big_card_url;
    }

    public void setBig_card_url(String big_card_url) {
        this.big_card_url = (big_card_url == null ? "" : big_card_url);
    }

    public String getJump_url() {
        return jump_url == null ? "" : jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = (jump_url == null ? "" : jump_url);
    }

    public FanBean getFan() {
        return fan==null?new FanBean():fan;
    }

    public void setFan(FanBean fan) {
        this.fan = fan;
    }

    public String getImage_enhance() {
        return image_enhance == null ? "" : image_enhance;
    }

    public void setImage_enhance(String image_enhance) {
        this.image_enhance = (image_enhance == null ? "" : image_enhance);
    }

    public static class FanBean {
        /**
         * is_fan : 0
         * number : 0
         * color :
         * num_desc :
         */

        private int is_fan;
        private int number;
        private String color;
        private String num_desc;

        public int getIs_fan() {
            return is_fan;
        }

        public void setIs_fan(int is_fan) {
            this.is_fan = is_fan;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getColor() {
            return color == null ? "" : color;
        }

        public void setColor(String color) {
            this.color = (color == null ? "" : color);
        }

        public String getNum_desc() {
            return num_desc == null ? "" : num_desc;
        }

        public void setNum_desc(String num_desc) {
            this.num_desc = (num_desc == null ? "" : num_desc);
        }
    }
}
