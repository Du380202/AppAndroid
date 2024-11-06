package com.example.bookstore.dto;

public class User {
    private int img;
    private String userName;
    private String email;
    private String status;

    public User(int img, String userName, String email, String status) {
        this.img = img;
        this.userName = userName;
        this.email = email;
        this.status = status;
    }

    public User(int img, String userName, String email) {
        this.img = img;
        this.userName = userName;
        this.email = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
