package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private String orderPaymentType;
    private String orderStatus;
    private Double orderAmount;
    @OneToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;
    private String transactionId;

    public OrderDetail() {

    }

    public OrderDetail(String orderPaymentType, String orderStatus, Double orderAmount, Product product, User user) {
        this.orderPaymentType = orderPaymentType;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.product = product;
        this.user = user;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderPaymentType() {
        return orderPaymentType;
    }

    public void setOrderPaymentType(String orderPaymentType) {
        this.orderPaymentType = orderPaymentType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
}