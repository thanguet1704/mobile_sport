package com.example.mobilesporta.model;

import java.util.List;

public class Stadium {
    private String stadium_name;
    private String description;
    private String address;
    private Integer amount;
    private Integer cost;
    private String image;
    private String phone_number;
    private String time_open;
    private String time_close;
    private String location_x;
    private String location_y;

    private List<StadiumComment> listComments;
    private String zone;

    public Stadium() {
    }

    public Stadium(String stadium_name, String description, String address, Integer amount, Integer cost, String image, String phone_number, String time_open, String time_close, String location_x, String location_y, List<StadiumComment> listComments, String zone) {
        this.stadium_name = stadium_name;
        this.description = description;
        this.address = address;
        this.amount = amount;
        this.cost = cost;
        this.image = image;
        this.phone_number = phone_number;
        this.time_open = time_open;
        this.time_close = time_close;
        this.location_x = location_x;
        this.location_y = location_y;
        this.listComments = listComments;
        this.zone = zone;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public Stadium setStadium_name(String stadium_name) {
        this.stadium_name = stadium_name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stadium setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Stadium setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public Stadium setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Integer getCost() {
        return cost;
    }

    public Stadium setCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Stadium setImage(String image) {
        this.image = image;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Stadium setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getTime_open() {
        return time_open;
    }

    public Stadium setTime_open(String time_open) {
        this.time_open = time_open;
        return this;
    }

    public String getTime_close() {
        return time_close;
    }

    public Stadium setTime_close(String time_close) {
        this.time_close = time_close;
        return this;
    }

    public String getLocation_x() {
        return location_x;
    }

    public Stadium setLocation_x(String location_x) {
        this.location_x = location_x;
        return this;
    }

    public String getLocation_y() {
        return location_y;
    }

    public Stadium setLocation_y(String location_y) {
        this.location_y = location_y;
        return this;
    }

    public List<StadiumComment> getComments() {
        return listComments;
    }

    public Stadium setComments(List<StadiumComment> listComments) {
        this.listComments = listComments;
        return this;
    }

    public String getZone() {
        return zone;
    }

    public Stadium setZone(String zone) {
        this.zone = zone;
        return this;
    }
}