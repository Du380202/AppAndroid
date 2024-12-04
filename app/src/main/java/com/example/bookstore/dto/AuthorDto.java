package com.example.bookstore.dto;

public class AuthorDto {
    private int authorId;
    private String authorName;
    private String yearBirthday;
    private String authorImg;

    public AuthorDto(String authorName, String yearBirthday) {
        this.yearBirthday = yearBirthday;
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
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
