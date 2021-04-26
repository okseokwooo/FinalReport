package com.example.gajdadeulim;

public class NoticeData {
    private String num;
    private String title;
    private String content;
    private String time;

    public NoticeData(String num, String title, String content, String time) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}

