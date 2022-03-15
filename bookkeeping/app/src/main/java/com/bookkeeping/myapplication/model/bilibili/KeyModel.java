package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/7/15  11:44
 * @desc :
 */
public class KeyModel {

    /**
     * hash : dcef044d36eba389
     * key : -----BEGIN PUBLIC KEY-----
     MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjb4V7EidX/ym28t2ybo0U6t0n
     6p4ej8VjqKHg100va6jkNbNTrLQqMCQCAYtXMXXp2Fwkk6WR+12N9zknLjf+C9sx
     /+l48mjUU8RqahiFD1XT/u2e0m2EN029OhCgkHx3Fc/KlFSIbak93EH/XlYis0w+
     Xl69GV6klzgxW6d2xQIDAQAB
     -----END PUBLIC KEY-----

     */

    private String hash;
    private String key;

    public String getHash() {
        return hash == null ? "" : hash;
    }

    public void setHash(String hash) {
        this.hash = (hash == null ? "" : hash);
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = (key == null ? "" : key);
    }
}
