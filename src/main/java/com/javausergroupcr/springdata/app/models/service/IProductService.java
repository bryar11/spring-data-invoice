package com.javausergroupcr.springdata.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.javausergroupcr.springdata.app.models.entity.Product;

public interface IProductService {
	
	public Page<Product> findAll(Pageable pageable);
	
	public Page<Product> findAllByName(String term, Pageable pageable);
	
	public List<Product> findAllByName(String term);
	
	public Product findById(Long id);

	public void save(Product product);

}
