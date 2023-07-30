package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import java.util.List;

public class OrderInput {

    private String paymentMethod;
    private List<OrderProductQuantity> orderProductQuantityList;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderProductQuantity> getOrderProductQuantityList() {
        return orderProductQuantityList;
    }

    public void setOrderProductQuantityList(List<OrderProductQuantity> orderProductQuantityList) {
        this.orderProductQuantityList = orderProductQuantityList;
    }
}