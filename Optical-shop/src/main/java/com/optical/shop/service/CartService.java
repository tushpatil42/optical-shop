package com.optical.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optical.shop.dao.CartItemRepository;
import com.optical.shop.entities.CartItem;

@Service
public class CartService {

	@Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
    
    public int getCartCount() {
        return (int) cartItemRepository.count();
    }
    
    public double getCartTotal() {
        return cartItemRepository.findAll().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
