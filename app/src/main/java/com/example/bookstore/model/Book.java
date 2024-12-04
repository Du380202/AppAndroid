package com.example.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private Integer bookId;
    private String bookName;
    private BigDecimal price;
    private String daySaleDate;
    private String image;
    private String description;
    private Integer quantity;
    private Integer publicationYear;
    private Integer status;
    private Float rating;
//    private List<OrderDetail> orderDetails = new ArrayList<>();
    private Publisher publisher;
    private List<Category> categories = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
//
//    private List<Discount> discounts = new ArrayList<>();
//
//    private List<Rate> rates = new ArrayList<>();
//
//    private List<Cart> cart = new ArrayList<>();


    public String getDaySaleDate() {
        return daySaleDate;
    }

    public void setDaySaleDate(String daySaleDate) {
        this.daySaleDate = daySaleDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Book(Integer bookId, String bookName, BigDecimal price, String image, String description, Integer quantity, Integer publicationYear, Integer status, Float rating) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.publicationYear = publicationYear;
        this.status = status;
        this.rating = rating;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", publicationYear=" + publicationYear +
                ", status=" + status +
                ", rating=" + rating +
                '}';
    }
}
