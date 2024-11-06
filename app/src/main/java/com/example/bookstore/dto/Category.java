package com.example.bookstore.dto;

public class Category {
    private int imgId;
    private String categoryName;
    private Integer quantityBook;

    public Category(int imgId, String categoryName, Integer quantityBook) {
        this.imgId = imgId;
        this.categoryName = categoryName;
        this.quantityBook = quantityBook;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getQuantityBook() {
        return quantityBook;
    }

    public void setQuantityBook(Integer quantityBook) {
        this.quantityBook = quantityBook;
    }
}
