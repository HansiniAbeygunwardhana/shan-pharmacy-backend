package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.configuration.JwtRequestFilter;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.CartDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.OrderDetailDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.UserDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.*;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    private ProductRepository productRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> getAllOrderDetails(@RequestParam(name = "status", defaultValue = "All") String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if (status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


        return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).orElseThrow(() -> new RuntimeException("User not found"));

        // If it's a single product checkout, place an order for that product only
        if (isSingleProductCheckout) {
            for (OrderProductQuantity o : productQuantityList) {
                Product product = productRepository.findById(o.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

                OrderDetail orderDetail = new OrderDetail(
                        orderInput.getPaymentMethod(),
                        ORDER_PLACED,
                        product.getProductPrice() * o.getQuantity(),
                        product,
                        user
                );

                orderDetailDao.save(orderDetail);
            }
        } else {
            // If it's not a single product checkout, place an order for all the products in the cart
            List<Cart> carts = cartDao.findByUser(user);
            for (Cart cart : carts) {
                Product product = cart.getProduct();
                OrderDetail orderDetail = new OrderDetail(
                        orderInput.getPaymentMethod(),
                        ORDER_PLACED,
                        product.getProductPrice() * cart.getQuantity(),
                        product,
                        user
                );

                orderDetailDao.save(orderDetail);
                cartDao.delete(cart);
            }
        }
    }


    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if (orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

//    public TransactionDetails createTransaction(Double amount) {
//        try {
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("amount", (amount * 100) );
//            jsonObject.put("currency", CURRENCY);
//
//            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
//
//            Order order = razorpayClient.orders.create(jsonObject);
//
//            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
//            return transactionDetails;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//    private TransactionDetails prepareTransactionDetails(Order order) {
//        String orderId = order.get("id");
//        String currency = order.get("currency");
//        Integer amount = order.get("amount");
//
//        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
//        return transactionDetails;
//    }
}