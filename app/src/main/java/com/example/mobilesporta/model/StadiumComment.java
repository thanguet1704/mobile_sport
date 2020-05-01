package com.example.mobilesporta.model;

public class StadiumComment {

    private String user_id;
    private String content;

    public StadiumComment() {
    }

    public StadiumComment(String user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public StadiumComment setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public StadiumComment setContent(String content) {
        this.content = content;
        return this;
    }
}
