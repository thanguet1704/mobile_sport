package com.example.mobilesporta.model;

public class NewsModel {

    private String title;
    private String content;
    private String image;

    public NewsModel() {
    }

    public NewsModel(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public NewsModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NewsModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImage() {
        return image;
    }

    public NewsModel setImage(String image) {
        this.image = image;
        return this;
    }
}
