package com.bookkeeping.myapplication.model.bilibili;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/3/26  17:24
 * @desc :
 */
public class ContentModel {

    /**
     * message : 道爷，小弟这边建议你把那些发 羡慕 的，全部抓起来，然后每人出一期[滑稽]
     * plat : 3
     * device : phone
     * members : []
     * emote : {"[滑稽]":{"id":27,"package_id":1,"state":0,"type":1,"attr":0,"text":"[滑稽]","url":"http://i0.hdslb.com/bfs/emote/d15121545a99ac46774f1f4465b895fe2d1411c3.png","meta":{"size":1,"suggest":[""]},"mtime":1615789854}}
     * jump_url : {}
     * max_line : 6
     */

    private String message;
    private int plat;
    private String device;
    private String emote;
    private String jump_url;
    private List<EmoteModel> emoteList;
    private List<JumpUrlModel> jumpUrlList;
    private int max_line;
    private List<MemberModel> members;

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPlat() {
        return plat;
    }

    public void setPlat(int plat) {
        this.plat = plat;
    }

    public String getDevice() {
        return device == null ? "" : device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEmote() {
        return emote == null ? "" : emote;
    }

    public List<EmoteModel> getEmoteList() {
        if (emoteList == null) {
            return new ArrayList<>();
        }
        return emoteList;
    }

    public void setEmoteList(List<EmoteModel> emoteList) {
        this.emoteList = emoteList;
    }

    public List<JumpUrlModel> getJumpUrlList() {
        if (jumpUrlList == null) {
            return new ArrayList<>();
        }
        return jumpUrlList;
    }

    public void setJumpUrlList(List<JumpUrlModel> jumpUrlList) {
        this.jumpUrlList = jumpUrlList;
    }

    public void setEmote(String emote) {
        this.emote = emote == null ? "" : emote;
    }

    public String getJump_url() {
        return jump_url == null ? "" : jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url == null ? "" : jump_url;
    }

    public List<MemberModel> getMembers() {
        if (members == null) {
            return new ArrayList<>();
        }
        return members;
    }

    public void setMembers(List<MemberModel> members) {
        this.members = members;
    }

    public int getMax_line() {
        return max_line;
    }

    public void setMax_line(int max_line) {
        this.max_line = max_line;
    }
}
