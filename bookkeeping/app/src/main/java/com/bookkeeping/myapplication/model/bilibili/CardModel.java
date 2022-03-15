package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/9  10:14
 * @desc :动态详情
 */
public class CardModel {

    /**
     * desc : {}
     * card : {"item":{"at_control":"","category":"daily","description":"BW 闪现表。 \n10点开始在 碧蓝航线\n出 安克雷奇！ \n\n1100-1130 休息 \n1200-1245 大升格（除11号）\n1400-1500 大升格（除11号）\n\n没标注的时间点都在碧蓝。 \n也会可能到处逛 不知道。 \n\n〖在大升格售后服务区，对暗号“天下第一可爱”可以随机拿到台历\/钥匙扣〗可能下午才有\n⬆️ 数量有限 （台历：5 钥匙扣：5）\n\n下班可能也会去吧 不知道 看心情。 ","id":150202589,"is_fav":0,"pictures":[{"img_height":1935,"img_size":1352.349609375,"img_src":"https:\/\/i0.hdslb.com\/bfs\/album\/557cce8f8b746d114ef93cc7a1ab304b1653b7f8.jpg","img_tags":null,"img_width":2541}],"pictures_count":1,"reply":43,"role":[],"settings":{"copy_forbidden":"0"},"source":[],"title":"","upload_time":1625795330},"user":{"head_url":"https:\/\/i2.hdslb.com\/bfs\/face\/2cd5ad6ab0c61eeba3fd250725c7f14c4ff8d435.jpg","name":"养乌龙猫的欠欠","uid":3306866,"vip":{"avatar_subscript":1,"due_date":1684771200000,"label":{"label_theme":"annual_vip","path":"","text":"年度大会员"},"nickname_color":"#FB7299","status":1,"theme_type":0,"type":2,"vip_pay_type":0}}}
     * extend_json : {"from":{"emoji_type":1,"from":"","up_close_comment":0,"verify":{"cc":{"nv":1}}},"like_icon":{"action":"","action_url":"","end":"","end_url":"","start":"","start_url":""}}
     * display : {}
     */

    private DescModel desc;
    private String card;
    private ExtendJsonBean extension;
    private String extend_json;
    private DisplayModel display;

    public DescModel getDesc() {
        return desc;
    }

    public void setDesc(DescModel desc) {
        this.desc = desc;
    }

    public String getCard() {
        return card == null ? "" : card;
    }

    public void setCard(String card) {
        this.card = (card == null ? "" : card);
    }

    public String getExtend_json() {
        return extend_json == null ? "" : extend_json;
    }

    public void setExtend_json(String extend_json) {
        this.extend_json = (extend_json == null ? "" : extend_json);
    }

    public DisplayModel getDisplay() {
        return display;
    }

    public void setDisplay(DisplayModel display) {
        this.display = display;
    }


    public static class ExtendJsonBean {

        /**
         * lott : {"callbackId":10,"lottery_id":66205,"lottery_time":1626674100,"title":"互动抽奖"}
         */

        private String lott;

        public String getLott() {
            return lott == null ? "" : lott;
        }

        public void setLott(String lott) {
            this.lott = (lott == null ? "" : lott);
        }
    }
}
