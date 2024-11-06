package com.optical.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optical.shop.entities.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

