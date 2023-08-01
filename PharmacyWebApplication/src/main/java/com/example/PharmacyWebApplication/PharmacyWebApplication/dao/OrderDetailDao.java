package com.example.PharmacyWebApplication.PharmacyWebApplication.dao;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.OrderDetail;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);

    List<OrderDetail> findByOrderPlacedAtBetween(Date startDate, Date endDate);

    List<OrderDetail> findByOrderPlacedAtAfter(Date date);
}