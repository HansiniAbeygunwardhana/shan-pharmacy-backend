package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "product_name")
    private @NotBlank String productName;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "rating")
    private Integer productRating;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "is_prescription_med")
    private Boolean isPrescriptionMed;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name="expireDate")
    private LocalDate expDate;
    private Double threshold;

    // Many-to-one relationship
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product") // One product can have multiple orders associated with it
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product") // One product can have multiple orders associated with it
    private List<Cart> cart;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Boolean isAvailable() {
        return isAvailable != null && isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean isPrescriptionMed() {
        return isPrescriptionMed != null && isPrescriptionMed;
    }

    public void setPrescriptionMed(Boolean prescriptionMed) {
        isPrescriptionMed = prescriptionMed;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
