package com.example.bookstore.dto;

public class Author {
    private int imgId;
    private String authorName;
    private String yearBirthday;

    public Author(int imgId,  String authorName, String yearBirthday) {
        this.imgId = imgId;
        this.yearBirthday = yearBirthday;
        this.authorName = authorName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getYearBirthday() {
        return yearBirthday;
    }

    public void setYearBirthday(String yearBirthday) {
        this.yearBirthday = yearBirthday;
    }
}
