package com.example.bookstore.dto;

public class Order {
    private String orderId;
    private String customerName;
    private String phoneNumber;
    private String shippingAddress;
    private String orderStatus;
    private String products;
    private String ngaydat;
    private String total;

    public String getTotal() {
        return total;
    }

    public Order(String orderId, String orderStatus, String ngaydat, String total) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.ngaydat = ngaydat;
        this.total = total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    // Constructor, getters, and setters
    public Order(String orderId, String customerName, String phoneNumber, String shippingAddress, String orderStatus, String products) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getShippingAddress() { return shippingAddress; }
    public String getOrderStatus() { return orderStatus; }
    public String getProducts() { return products; }
}
