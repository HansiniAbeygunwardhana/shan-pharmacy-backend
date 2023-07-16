package com.example.PharmacyWebApplication.PharmacyWebApplication.dao;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}