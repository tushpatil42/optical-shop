package com.optical.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optical.shop.dao.CustomerRepository;
import com.optical.shop.entities.Customer;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	public List<Customer> findAll() {
		return customerRepo.findAll();
	}

	public Customer findById(Long cutomerId) {
		return customerRepo.findById(cutomerId).orElse(null);
	}

	public void saveCustomer(Customer customer) {
		customerRepo.save(customer);
	}
}
