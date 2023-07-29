package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.configuration.JwtRequestFilter;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.CartDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.UserDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Cart;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserDao userDao;

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }

    public Cart addToCart(int id) {
        Product product = productRepository.findById(id).orElse(null);

        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if (username != null) {
            user = userDao.findById(username).orElse(null);
        }

        if (product != null && user != null) {
            // Check if the same product is already in the user's cart
            List<Cart> cartList = cartDao.findByUser(user);
            for (Cart cart : cartList) {
                if (cart.getProduct().getId() == id) {
                    return cart; // Return the existing cart item
                }
            }

            // If the product is not already in the cart, add it
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }

        return null;
    }


    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}