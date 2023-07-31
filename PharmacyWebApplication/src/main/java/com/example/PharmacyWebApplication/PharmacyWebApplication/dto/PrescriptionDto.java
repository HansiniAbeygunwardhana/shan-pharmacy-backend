package com.example.PharmacyWebApplication.PharmacyWebApplication.dto;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import jakarta.persistence.*;

public class PrescriptionDto {
    private Integer prescriptionId;
    private String frequency;
    private String fulfilment;
    private String substitute;
    private String prescriptionImageUrl;
    private String userName; // To store the ID of the associated user

    // Constructors, getters, and setters

    public PrescriptionDto() {
        // Default constructor
    }

    public PrescriptionDto(Integer prescriptionId, String frequency, String fulfilment, String substitute, String prescriptionImageUrl, String userName) {
        this.prescriptionId = prescriptionId;
        this.frequency = frequency;
        this.fulfilment = fulfilment;
        this.substitute = substitute;
        this.prescriptionImageUrl = prescriptionImageUrl;
        this.userName = userName;
    }

    // Additional constructor with fewer fields
    public PrescriptionDto(Integer prescriptionId, String frequency, String fulfilment) {
        this.prescriptionId = prescriptionId;
        this.frequency = frequency;
        this.fulfilment = fulfilment;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

