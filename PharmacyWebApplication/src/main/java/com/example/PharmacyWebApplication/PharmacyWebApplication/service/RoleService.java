package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.RoleDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}