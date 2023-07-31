package com.example.PharmacyWebApplication.PharmacyWebApplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prescriptionId;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "fulfillment")
    private String fulfilment;

    @Column(name = "substitute")
    private String substitute;

    @Column(name = "prescription_image_url")
    private String prescriptionImageUrl;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    public Prescription() {
        // Default constructor
    }

    public Prescription(String frequency, String fulfilment, String substitute, String prescriptionImageUrl, User user) {
        this.frequency = frequency;
        this.fulfilment = fulfilment;
        this.substitute = substitute;
        this.prescriptionImageUrl = prescriptionImageUrl;
        this.user = user;
    }

    // Additional constructor with User and other fields
    public Prescription(User user, String frequency, String fulfilment) {
        this.user = user;
        this.frequency = frequency;
        this.fulfilment = fulfilment;
        // Set default values for other fields if needed
        this.substitute = "N/A";
        this.prescriptionImageUrl = "default_image_url.jpg";
    }

    // Constructor with prescriptionImageUrl
    public Prescription(String prescriptionImageUrl) {
        this.prescriptionImageUrl = prescriptionImageUrl;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFulfilment() {
        return fulfilment;
    }

    public void setFulfilment(String fulfilment) {
        this.fulfilment = fulfilment;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }

    public String getPrescriptionImageUrl() {
        return prescriptionImageUrl;
    }

    public void setPrescriptionImageUrl(String prescriptionImageUrl) {
        this.prescriptionImageUrl = prescriptionImageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
