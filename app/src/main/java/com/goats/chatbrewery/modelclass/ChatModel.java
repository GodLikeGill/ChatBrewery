package com.goats.chatbrewery.modelclass;

public class ChatModel {

    private String msg;
    private String uid;
    private Long time;

    public ChatModel() {
    }

    public ChatModel(String msg, String uid, Long time) {
        this.msg = msg;
        this.uid = uid;
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
