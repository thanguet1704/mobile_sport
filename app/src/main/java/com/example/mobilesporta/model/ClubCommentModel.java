package com.example.mobilesporta.model;

public class ClubCommentModel {

    private String user_id;
    private String content;

    public ClubCommentModel() {
    }

    public ClubCommentModel(String user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public ClubCommentModel setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ClubCommentModel setContent(String content) {
        this.content = content;
        return this;
    }
}
