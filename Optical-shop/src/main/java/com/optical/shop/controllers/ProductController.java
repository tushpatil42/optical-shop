package com.optical.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.optical.shop.entities.Customer;
import com.optical.shop.entities.Product;
import com.optical.shop.service.CartService;
import com.optical.shop.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService proService;
	
    @Autowired
    private CartService cartService;

	/*
	 * @Autowired CustomerRepository custRepo;
	 */

	@GetMapping()
	public String displayProducts(Model model) {

		List<Product> products = proService.findAll();
		model.addAttribute("products", products);
		model.addAttribute("cartCount", cartService.getCartCount());
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("cartTotal", cartService.getCartTotal());
        
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "main/home";

	}

}
