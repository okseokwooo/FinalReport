package com.example.gajdadeulim;

public class ChattingContentData {
    private int iv_profile;
//    private String sendID = "";
    private String userID = "";
    private int num;
    private String chatText = "";
    private String c_Time ="";
//    private String sendText = "";
//    private String sendTime ="";

    public ChattingContentData(int iv_profile, int num, String userID, String chatText, String c_Time) {
        this.num = num;
        this.userID = userID;
        this.chatText = chatText;
        this.c_Time = c_Time;
//        this.sendID = sendID;
//        this.sendText = sendText;
//        this.sendTime = sendTime;
        this.iv_profile = iv_profile;
    }

    public int getIv_profile() { return iv_profile; }

    public void setIv_profile(int iv_profile) { this.iv_profile = iv_profile; }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getC_Time() {
        return c_Time;
    }

    public void setC_Time(String c_Time) {
        this.c_Time = c_Time;
    }

//    public String getSendID() {
//        return sendID;
//    }
//
//    public void setSendID(String sendID) {
//        this.sendID = sendID;
//    }
//
//    public String getSendText() {
//        return sendText;
//    }
//
//    public void setSendText(String sendText) {
//        this.sendText = sendText;
//    }
//
//    public String getSendTime() {
//        return sendTime;
//    }
//
//    public void setSendTime(String sendTime) {
//        this.sendTime = sendTime;
//    }
}
