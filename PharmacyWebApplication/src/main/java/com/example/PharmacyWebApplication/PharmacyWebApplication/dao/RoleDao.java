package com.example.PharmacyWebApplication.PharmacyWebApplication.dao;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}