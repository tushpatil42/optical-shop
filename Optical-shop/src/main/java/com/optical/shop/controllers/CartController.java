package com.optical.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.optical.shop.entities.CartItem;
import com.optical.shop.entities.Customer;
import com.optical.shop.entities.Product;
import com.optical.shop.service.CartService;
import com.optical.shop.service.ProductService;

@Controller
public class CartController {
	
	@Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getProductId());
            cartItem.setProductName(product.getProductName());
            cartItem.setPrice(product.getProductPrice());
            cartItem.setQuantity(1); // Set quantity to 1 for simplicity
            cartService.addToCart(cartItem);
        }
        return "redirect:/products";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("cartCount", cartService.getCartCount());
        model.addAttribute("cartTotal", cartService.getCartTotal());
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
        return "cart/cart";
    }
    
    @PostMapping("/place-order")
    public String placeOrder() {
        // Logic to place the order would go here
        // For simplicity, we'll just clear the cart after placing the order
        cartService.clearCart();
        return "redirect:/products"; // Redirect to products after placing the order
    }

}
