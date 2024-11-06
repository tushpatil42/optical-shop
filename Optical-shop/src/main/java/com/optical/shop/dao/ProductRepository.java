package com.optical.shop.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.optical.shop.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	@Override
	List<Product> findAll();

}
