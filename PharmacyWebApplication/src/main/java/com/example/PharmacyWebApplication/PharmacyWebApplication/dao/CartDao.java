package com.example.PharmacyWebApplication.PharmacyWebApplication.dao;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Cart;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer > {
    public List<Cart> findByUser(User user);
}