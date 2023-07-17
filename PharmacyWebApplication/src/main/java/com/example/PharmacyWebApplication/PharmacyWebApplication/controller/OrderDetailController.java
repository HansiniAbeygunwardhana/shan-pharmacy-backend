package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.OrderDetail;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.OrderInput;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('user')")
    @PostMapping({"/placeOrder"})
    public void placeOrder(@RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput);
    }

//    @PreAuthorize("hasRole('user')")
//    @GetMapping({"/getOrderDetails"})
//    public List<OrderDetail> getOrderDetails() {
//        return orderDetailService.getOrderDetails();
//    }
//
//    @PreAuthorize("hasRole('admin')")
//    @GetMapping({"/getAllOrderDetails/{status}"})
//    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
//        return orderDetailService.getAllOrderDetails(status);
//    }
//
//    @PreAuthorize("hasRole('admin')")
//    @GetMapping({"/markOrderAsDelivered/{orderId}"})
//    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
//        orderDetailService.markOrderAsDelivered(orderId);
//    }
//
//    @PreAuthorize("hasRole('user')")
//    @GetMapping({"/createTransaction/{amount}"})
//    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
//        return orderDetailService.createTransaction(amount);
//    }
}