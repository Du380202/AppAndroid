package com.example.bookstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Publisher implements Serializable {

    private Integer publisherId;
    private String publisherName;
    private String address;
    private String hotline;
    private String email;
//    private List<Book> books = new ArrayList<>();


    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
