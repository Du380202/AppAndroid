package com.example.bookstore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BookDto {
    private int bookId;
    private String bookName;
    private BigDecimal price;
    private String description;
    private int quantity;
    private String image;
    private int publicationYear;
    private String daySaleDate;
    private int status;
    private int publisherId;
    private float rating;

    private List<Integer> categoryIds;
    private List<Integer> authorId;

    public int getBookId() {
        return bookId;
    }

    public String getDaySaleDate() {
        return daySaleDate;
    }

    public void setDaySaleDate(String daySaleDate) {
        this.daySaleDate = daySaleDate;
    }

    public BookDto(int bookId, String bookName, BigDecimal price, String description, int quantity, int publicationYear, String daySaleDate, int status, int publisherId, float rating, List<Integer> categoryIds, List<Integer> authorId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.publicationYear = publicationYear;
        this.daySaleDate = daySaleDate;
        this.status = status;
        this.publisherId = publisherId;
        this.rating = rating;
        this.categoryIds = categoryIds;
        this.authorId = authorId;
    }

    public BookDto(String bookName, BigDecimal price, String description, int quantity, int publicationYear, int status, int publisherId, float rating, List<Integer> categoryIds, List<Integer> authorId, String daySaleDate) {
        this.bookName = bookName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.publicationYear = publicationYear;
        this.status = status;
        this.publisherId = publisherId;
        this.rating = rating;
        this.categoryIds = categoryIds;
        this.authorId = authorId;
        this.daySaleDate = daySaleDate;
    }

    public void setBookId(int bookId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Integer> getAuthorId() {
        return authorId;
    }

    public void setAuthorId(List<Integer> authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", publicationYear=" + publicationYear +
                ", daySaleDate='" + daySaleDate + '\'' +
                ", status=" + status +
                ", publisherId=" + publisherId +
                ", rating=" + rating +
                ", categoryIds=" + categoryIds +
                ", authorId=" + authorId +
                '}';
    }
}
