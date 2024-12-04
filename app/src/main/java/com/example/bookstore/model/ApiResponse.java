package com.example.bookstore.model;

public class ApiResponse {
	private int status;
    private String message;

 
	public ApiResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
