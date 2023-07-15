package com.example.PharmacyWebApplication.PharmacyWebApplication.repository;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findNameByCategoryName(String categoryName);
}