package com.example.bookstore.dto;

import java.time.LocalDate;
import java.util.List;


public class DiscountDto {
	private Integer discountId;
	private String discountName;
	private String description;
	private Integer status;
	private String startDate;
	private String endDate;
	private Integer discountPercent;

	private List<Integer> bookIds;
	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Integer getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getBookIds() {
		return bookIds;
	}

	public void setBookIds(List<Integer> bookIds) {
		this.bookIds = bookIds;
	}

	@Override
	public String toString() {
		return "DiscountDto{" +
				"discountId=" + discountId +
				", discountName='" + discountName + '\'' +
				", description='" + description + '\'' +
				", status=" + status +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", bookIds=" + bookIds +
				'}';
	}
}
