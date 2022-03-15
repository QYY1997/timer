package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/4/2  17:25
 * @desc :
 */
public class JumpUrlModel {

    /**
     * title : 【逗鱼时刻】第62期 会长的励志故事
     * state : 0
     * prefix_icon : https://i0.hdslb.com/bfs/activity-plat/static/20201110/4c8b2dbaded282e67c9a31daa4297c3c/AeQJlYP7e.png
     * app_url_schema :
     * app_name :
     * app_package_name :
     * click_report : 4008240
     */

    private String title;
    private int state;
    private String prefix_icon;
    private String app_url_schema;
    private String app_name;
    private String app_package_name;
    private String click_report;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPrefix_icon() {
        return prefix_icon == null ? "" : prefix_icon;
    }

    public void setPrefix_icon(String prefix_icon) {
        this.prefix_icon = prefix_icon == null ? "" : prefix_icon;
    }

    public String getApp_url_schema() {
        return app_url_schema == null ? "" : app_url_schema;
    }

    public void setApp_url_schema(String app_url_schema) {
        this.app_url_schema = app_url_schema == null ? "" : app_url_schema;
    }

    public String getApp_name() {
        return app_name == null ? "" : app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name == null ? "" : app_name;
    }

    public String getApp_package_name() {
        return app_package_name == null ? "" : app_package_name;
    }

    public void setApp_package_name(String app_package_name) {
        this.app_package_name = app_package_name == null ? "" : app_package_name;
    }

    public String getClick_report() {
        return click_report == null ? "" : click_report;
    }

    public void setClick_report(String click_report) {
        this.click_report = click_report == null ? "" : click_report;
    }
}
