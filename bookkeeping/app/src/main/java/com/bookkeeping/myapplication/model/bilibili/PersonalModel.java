package com.bookkeeping.myapplication.model.bilibili;

/**
 * @author : qiuyiyang
 * @date : 2021/8/11  17:52
 * @desc :
 */
public class PersonalModel {

    /**
     * mid : 642922
     * attribute : 2
     * mtime : 1603021738
     * tag : null
     * special : 0
     * contract_info : {"is_contractor":false,"ts":0,"is_contract":false}
     * uname : 韩小沐
     * face : http://i1.hdslb.com/bfs/face/1bb9878904ff3e6a86db29ece714ff555a54eccf.jpg
     * sign : 多谢一路上漫天星光！
     群801749878，微博@韩小沐_要当个好店长
     * official_verify : {}
     * vip : {}
     */

    private String mid;
    private int attribute;
    private long mtime;
    private Object tag;
    private int special;
    private ContractInfoBean contract_info;
    private String uname;
    private String face;
    private String sign;
    private OfficialVerifyModel official_verify;
    private VipPersonModel vip;

    public String getMid() {
        return mid == null ? "" : mid;
    }

    public void setMid(String mid) {
        this.mid = (mid == null ? "" : mid);
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public ContractInfoBean getContract_info() {
        return contract_info;
    }

    public void setContract_info(ContractInfoBean contract_info) {
        this.contract_info = contract_info;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public OfficialVerifyModel getOfficial_verify() {
        return official_verify;
    }

    public void setOfficial_verify(OfficialVerifyModel official_verify) {
        this.official_verify = official_verify;
    }

    public VipPersonModel getVip() {
        return vip;
    }

    public void setVip(VipPersonModel vip) {
        this.vip = vip;
    }

    public static class ContractInfoBean {

        /**
         * is_contractor : false
         * ts : 0
         * is_contract : false
         */

        private boolean is_contractor;
        private int ts;
        private boolean is_contract;

        public boolean isIs_contractor() {
            return is_contractor;
        }

        public void setIs_contractor(boolean is_contractor) {
            this.is_contractor = is_contractor;
        }

        public int getTs() {
            return ts;
        }

        public void setTs(int ts) {
            this.ts = ts;
        }

        public boolean isIs_contract() {
            return is_contract;
        }

        public void setIs_contract(boolean is_contract) {
            this.is_contract = is_contract;
        }
    }


}
