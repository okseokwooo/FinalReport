package com.example.gajdadeulim;

public class ChattingListData {
    private int iv_profile;
    private String orderID = "";
    private String errandID = "";
    private int num;

    public ChattingListData(int iv_profile, int num , String orderID, String errandID) {
        this.num = num;
        this.orderID = orderID;
        this.iv_profile = iv_profile;
        this.errandID = errandID;
    }

    public int getIv_profile() { return iv_profile; }

    public void setIv_profile(int iv_profile) { this.iv_profile = iv_profile; }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getErrandID() {
        return errandID;
    }

    public void setErrandID(String errandID) {
        this.errandID = errandID;
    }
}
