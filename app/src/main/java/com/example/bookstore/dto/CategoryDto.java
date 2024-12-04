package com.example.bookstore.dto;

public class CategoryDto {
	private Integer categoryId;
	private String categoryName;
	private String depscription;
	private String categoryImg;

	public CategoryDto(String categoryName, String depscription) {
		this.categoryName = categoryName;
		this.depscription = depscription;
//		this.categoryImg = categoryImg;
	}

	public String getCategoryImg() {
		return categoryImg;
	}
	public void setCategoryImg(String categoryImg) {
		this.categoryImg = categoryImg;
	}
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
