package com.example.bookstore.model;

import java.io.Serializable;

public class Author implements Serializable {
    private Integer authorId;

    private String authorName;

    private String biography;

    private String authorImg;

//    private List<Book> bookList = new ArrayList<>();


    public Author(String authorName, String biography) {
        this.authorName = authorName;
        this.biography = biography;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
