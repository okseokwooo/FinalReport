package com.example.gajdadeulim;

public class Chat_Module {
    private int Num; // 구분하기위한것 의미x
    private String userID; // 채팅을 친 사용자 아이디
    private String ChattingText;
    private String C_time;
    private int C_number; // 채팅방을 구분

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    public String getChattingText() {
        return ChattingText;
    }

    public void setChattingText(String chattingText) {
        ChattingText = chattingText;
    }

    public String getC_time() {
        return C_time;
    }

    public void setC_time(String c_time) {
        C_time = c_time;
    }

    public int getC_number() {
        return C_number;
    }

    public void setC_number(int c_number) {
        C_number = c_number;
    }
}
