package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Cart;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('user')")
    @GetMapping({"/addToCart/{id}"})
    public Cart addToCart(@PathVariable(name = "id") int id,
                          @RequestParam(name = "quantity", required = false, defaultValue = "1") Double quantity) {
        // Call the addToCart method with the product id and quantity
        return cartService.addToCart(id, quantity);
    }

    @PreAuthorize("hasRole('user')")
    @PutMapping("/updateCartItemQuantity/{cartId}")
    public Cart updateCartItemQuantity(
            @PathVariable(name = "cartId") Integer cartId,
            @RequestParam(name = "quantity") Double quantity) {
        // Call the updateCartItemQuantity method with the cartId and new quantity
        return cartService.updateCartItemQuantity(cartId, quantity);
    }

    @PreAuthorize("hasRole('user')")
    @DeleteMapping({"/deleteCartItem/{cartId}"})
    public void deleteCartItem(@PathVariable(name = "cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping({"/getCartDetails"})
    public List<Cart> getCartDetails() {
        return cartService.getCartDetails();
    }
}