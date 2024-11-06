package com.example.bookstore.dto;

public class Publisher {
    private int publisherImg;
    private String tvPublisherName;
    private String tvPhone;

    public Publisher(int publisherImg, String tvPublisherName, String tvPhone) {
        this.publisherImg = publisherImg;
        this.tvPublisherName = tvPublisherName;
        this.tvPhone = tvPhone;
    }

    public int getPublisherImg() {
        return publisherImg;
    }

    public void setPublisherImg(int publisherImg) {
        this.publisherImg = publisherImg;
    }

    public String getTvPublisherName() {
        return tvPublisherName;
    }

    public void setTvPublisherName(String tvPublisherName) {
        this.tvPublisherName = tvPublisherName;
    }

    public String getTvPhone() {
        return tvPhone;
    }

    public void setTvPhone(String tvPhone) {
        this.tvPhone = tvPhone;
    }
}
