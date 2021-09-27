package com.example.recycleview.model;

public class UserMessage {
    public static final int TYPE_SEND = 1;
    //用户接收的消息
    public static final int TYPE_RECEIVE = 0;
    //消息内容
    private String content;
    //消息类型
    private int type;

    public UserMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
