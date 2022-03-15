package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  16:44
 * @desc :
 */
public class UserSailingModel {
    /**
     * pendant : {"id":1011,"name":"大航海_提督","image":"http://i0.hdslb.com/bfs/garb/item/417bc4afe270e9837511355b638d131ccd0852bc.png","jump_url":"","type":"sailing","image_enhance":"http://i0.hdslb.com/bfs/garb/item/417bc4afe270e9837511355b638d131ccd0852bc.png","image_enhance_frame":""}
     * cardbg : {"id":1007,"name":"大航海_提督","image":"http://i0.hdslb.com/bfs/garb/item/c836a92497a246849c16029582ba3c5076059b6b.png","jump_url":"https://live.bilibili.com/p/html/live-app-guard-info/index.html?is_live_webview=1&hybrid_set_header=2&data_behavior_id=guard_main_video&uid=295723","fan":{"is_fan":0,"number":0,"color":"","name":"","num_desc":""},"type":"sailing"}
     * cardbg_with_focus : {"id":1383,"name":"大航海_提督_关注","image":"http://i0.hdslb.com/bfs/garb/item/c2dff8b3bdbc9886c20c82180f725589be348106.png","jump_url":"https://live.bilibili.com/p/html/live-app-guard-info/index.html?is_live_webview=1&hybrid_set_header=2&data_behavior_id=guard_main_video&uid=295723","fan":{"is_fan":0,"number":0,"color":"","name":"","num_desc":""},"type":"sailing"}
     */
    private PendantBean pendant;
    private CardbgBean cardbg;
    private CardbgWithFocusBean cardbg_with_focus;

    public PendantBean getPendant() {
        return pendant==null?new PendantBean():pendant;
    }

    public void setPendant(PendantBean pendant) {
        this.pendant = pendant;
    }

    public CardbgBean getCardbg() {
        return cardbg==null?new CardbgBean():cardbg;
    }

    public void setCardbg(CardbgBean cardbg) {
        this.cardbg = cardbg;
    }

    public CardbgWithFocusBean getCardbg_with_focus() {
        return cardbg_with_focus==null?new CardbgWithFocusBean():cardbg_with_focus;
    }
    public void setCardbg_with_focus(CardbgWithFocusBean cardbg_with_focus) {
        this.cardbg_with_focus = cardbg_with_focus;
    }

    public static class PendantBean {
        /**
         * id : 1011
         * name : 大航海_提督
         * image : http://i0.hdslb.com/bfs/garb/item/417bc4afe270e9837511355b638d131ccd0852bc.png
         * jump_url :
         * type : sailing
         * image_enhance : http://i0.hdslb.com/bfs/garb/item/417bc4afe270e9837511355b638d131ccd0852bc.png
         * image_enhance_frame :
         */

        private String id;
        private String name;
        private String image;
        private String jump_url;
        private String type;
        private String image_enhance;
        private String image_enhance_frame;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getJump_url() {
            return jump_url == null ? "" : jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage_enhance() {
            return image_enhance == null ? "" : image_enhance;
        }

        public void setImage_enhance(String image_enhance) {
            this.image_enhance = image_enhance;
        }

        public String getImage_enhance_frame() {
            return image_enhance_frame == null ? "" : image_enhance_frame;
        }

        public void setImage_enhance_frame(String image_enhance_frame) {
            this.image_enhance_frame = image_enhance_frame;
        }
    }
    public static class CardbgBean {
        /**
         * id : 1007
         * name : 大航海_提督
         * image : http://i0.hdslb.com/bfs/garb/item/c836a92497a246849c16029582ba3c5076059b6b.png
         * jump_url : https://live.bilibili.com/p/html/live-app-guard-info/index.html?is_live_webview=1&hybrid_set_header=2&data_behavior_id=guard_main_video&uid=295723
         * fan : {"is_fan":0,"number":0,"color":"","name":"","num_desc":""}
         * type : sailing
         */

        private String id;
        private String name;
        private String image;
        private String jump_url;
        private FanBean fan;
        private String type;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getJump_url() {
            return jump_url == null ? "" : jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public FanBean getFan() {
            return fan==null?new FanBean():fan;
        }

        public void setFan(FanBean fan) {
            this.fan = fan;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }


    }
    public static class CardbgWithFocusBean {
        /**
         * id : 1383
         * name : 大航海_提督_关注
         * image : http://i0.hdslb.com/bfs/garb/item/c2dff8b3bdbc9886c20c82180f725589be348106.png
         * jump_url : https://live.bilibili.com/p/html/live-app-guard-info/index.html?is_live_webview=1&hybrid_set_header=2&data_behavior_id=guard_main_video&uid=295723
         * fan : {"is_fan":0,"number":0,"color":"","name":"","num_desc":""}
         * type : sailing
         */

        private String id;
        private String name;
        private String image;
        private String jump_url;
        private FanBean fan;
        private String type;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getJump_url() {
            return jump_url == null ? "" : jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public FanBean getFan() {
            return fan==null?new FanBean():fan;
        }

        public void setFan(FanBean fan) {
            this.fan = fan;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class FanBean {
        /**
         * is_fan : 0
         * number : 0
         * color :
         * name :
         * num_desc :
         */

        private int is_fan;
        private int number;
        private String color;
        private String name;
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
            this.color = color;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum_desc() {
            return num_desc == null ? "" : num_desc;
        }

        public void setNum_desc(String num_desc) {
            this.num_desc = num_desc;
        }
    }
}
