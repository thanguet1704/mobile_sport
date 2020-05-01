package com.example.mobilesporta.model;

public class News {

    private String title;
    private String content;
    private String image;

    public News() {
    }

    public News(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public News setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImage() {
        return image;
    }

    public News setImage(String image) {
        this.image = image;
        return this;
    }
}
