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

    public Cart addToCart(int productId, double quantity) {
        Product product = productRepository.findById(productId).orElse(null);

        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).orElse(null);

        // Set default quantity to 1 if not provided from the front end
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (product != null && user != null) {
            // Check if the same product is already in the user's cart
            List<Cart> cartList = cartDao.findByUser(user);
            for (Cart cart : cartList) {
                if (cart.getProduct().getId() == productId) {
                    // Update the cart item quantity
                    double newCartQuantity = cart.getQuantity() + quantity;
                    cart.setQuantity(newCartQuantity);

                    // Update the product quantity in the database
                    double newProductQuantity = product.getQuantity() - quantity;
                    if (newProductQuantity < 0) {
                        throw new IllegalArgumentException("Insufficient product quantity");
                    }
                    product.setQuantity(newProductQuantity);

                    productRepository.save(product);
                    return cartDao.save(cart); // Return the updated cart item
                }
            }

            // If the product is not already in the cart, add it with the given quantity
            if (quantity > 0) {
                // Check if the quantity is valid (greater than zero)
                Cart cart = new Cart(product, user, quantity);

                // Update the product quantity in the database
                double newProductQuantity = product.getQuantity() - quantity;
                if (newProductQuantity < 0) {
                    throw new IllegalArgumentException("Insufficient product quantity");
                }
                product.setQuantity(newProductQuantity);

                productRepository.save(product);
                return cartDao.save(cart);
            }
        }

        return null;
    }

    public Cart updateCartItemQuantity(Integer cartId, Double quantity) {
        Cart cart = cartDao.findById(cartId).orElse(null);
        if (cart != null) {
            // Get the original quantity of the cart item
            double originalQuantity = cart.getQuantity();

            // Get the corresponding product and user
            Product product = cart.getProduct();
            User user = cart.getUser();

            // Calculate the difference between the new quantity and the original quantity
            double quantityDifference = quantity - originalQuantity;

            // Update the cart item's quantity
            cart.setQuantity(quantity);

            // Update the product quantity in the database
            double newProductQuantity = product.getQuantity() - quantityDifference;
            if (newProductQuantity < 0) {
                throw new IllegalArgumentException("Insufficient product quantity");
            }
            product.setQuantity(newProductQuantity);

            productRepository.save(product);
            return cartDao.save(cart);
        }
        return null; // Cart item not found
    }


    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}