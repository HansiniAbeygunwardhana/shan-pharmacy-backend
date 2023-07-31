package com.example.PharmacyWebApplication.PharmacyWebApplication.repository;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Cart;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Prescription;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    // Add custom query methods if needed
    public List<Prescription> findByUser(User user);
}
