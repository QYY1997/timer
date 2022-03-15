package com.bookkeeping.myapplication.model.bilibili;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/12  17:46
 * @desc :标签
 */
public class TopicInfoModel{
    private List<TopicDetailsBean> topic_details;

    public List<TopicDetailsBean> getTopic_details() {
        return topic_details;
    }

    public void setTopic_details(List<TopicDetailsBean> topic_details) {
        this.topic_details = topic_details;
    }

    public static class TopicDetailsBean {
        /**
         * topic_id : 2908447
         * topic_name : 碧蓝航线
         * is_activity : 1
         * topic_link : https://www.bilibili.com/blackboard/dynamic/63094
         */

        private int topic_id;
        private String topic_name;
        private int is_activity;
        private String topic_link;

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name == null ? "" : topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = (topic_name == null ? "" : topic_name);
        }

        public int getIs_activity() {
            return is_activity;
        }

        public void setIs_activity(int is_activity) {
            this.is_activity = is_activity;
        }

        public String getTopic_link() {
            return topic_link == null ? "" : topic_link;
        }

        public void setTopic_link(String topic_link) {
            this.topic_link = (topic_link == null ? "" : topic_link);
        }
    }
}
