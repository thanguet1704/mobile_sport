package com.example.mobilesporta.model;

import java.util.List;

public class ClubModel {

    private String club_name;
    private String image;
    private String slogan;
    private String description;
    private String user_created_id;
    private Integer match_count;
    private List<ClubComment> listComments;

    public ClubModel() {
    }

    public ClubModel(String club_name, String image, String slogan, String description, String user_created_id, Integer match_count, List<ClubComment> listComments) {
        this.club_name = club_name;
        this.image = image;
        this.slogan = slogan;
        this.description = description;
        this.user_created_id = user_created_id;
        this.match_count = match_count;
        this.listComments = listComments;
    }

    public String getClub_name() {
        return club_name;
    }

    public ClubModel setClub_name(String club_name) {
        this.club_name = club_name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ClubModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getSlogan() {
        return slogan;
    }

    public ClubModel setSlogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ClubModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUser_created_id() {
        return user_created_id;
    }

    public ClubModel setUser_created_id(String user_created_id) {
        this.user_created_id = user_created_id;
        return this;
    }

    public Integer getMatch_count() {
        return match_count;
    }

    public ClubModel setMatch_count(Integer match_count) {
        this.match_count = match_count;
        return this;
    }

    public List<ClubComment> getListComment() {
        return listComments;
    }

    public ClubModel setListComment(List<ClubComment> listComment) {
        this.listComments = listComment;
        return this;
    }
}
