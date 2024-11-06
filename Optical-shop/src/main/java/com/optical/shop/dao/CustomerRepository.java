package com.optical.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optical.shop.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Override
	List<Customer> findAll();

}
