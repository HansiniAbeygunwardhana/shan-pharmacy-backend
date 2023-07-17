package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.configuration.JwtRequestFilter;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.OrderDetailDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.UserDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.*;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_AXBzvN2fkD4ESK";
    private static final String KEY_SECRET = "bsZmiVD7p1GMo6hAWiy4SHSH";
    private static final String CURRENCY = "INR";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private UserDao userDao;

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product = productRepository.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductPrice() * o.getQuantity(),
                    product,
                    user
            );

            orderDetailDao.save(orderDetail);
        }
    }
}
