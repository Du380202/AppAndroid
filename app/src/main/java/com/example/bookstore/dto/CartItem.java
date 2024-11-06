package com.example.bookstore.dto;

public class CartItem {
    private int imageResId;
    private String bookTitle;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

    public CartItem(int imageResId, String bookTitle, Double price, Integer quantity, Double totalPrice) {
        this.imageResId = imageResId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartItem(String bookTitle, Double price, Integer quantity, Double totalPrice) {
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
