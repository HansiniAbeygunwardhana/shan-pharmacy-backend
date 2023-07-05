package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // Many-to-one relationship
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

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

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
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
