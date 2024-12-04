package com.example.bookstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private String depscription;
    private String categoryImg;

    public String getCategoryImg() {
        return categoryImg;
    }

    public Category(Integer categoryId, String categoryName, String depscription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.depscription = depscription;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
//    private List<Book> books = new ArrayList<>();


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDepscription() {
        return depscription;
    }

    public void setDepscription(String depscription) {
        this.depscription = depscription;
    }
}
