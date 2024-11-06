package com.example.bookstore.dto;

public class Book {
    private String title;
    private String author;
    private String price;
    private String status;
    private int imageResId;

    // Constructor
    public Book(String title, String author, String price, String status, int imageResId) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.status = status;
        this.imageResId = imageResId;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    public int getImageResId() { return imageResId; }
}
