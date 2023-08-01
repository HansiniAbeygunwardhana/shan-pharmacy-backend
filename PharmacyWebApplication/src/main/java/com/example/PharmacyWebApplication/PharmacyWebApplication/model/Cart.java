package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import jakarta.persistence.*;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    private double quantity;

    // Default constructor with no arguments (required by JPA)
    public Cart() {
    }

    public Cart(Product product, User user, double quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

    // Getters and setters
    // ...

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
