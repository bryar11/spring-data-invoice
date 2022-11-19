package com.javausergroupcr.springdata.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

	public List<Product> findByNameContainingIgnoreCaseAndEnabledTrue(String term);

	public Page<Product> findAllByEnabledTrueOrderByNameAsc(Pageable pageable);

	public Page<Product> findAllByNameContainingIgnoreCaseAndEnabledTrueOrderByNameAsc(String name, Pageable pageable);
	
	public Product findByIdAndEnabledTrue(Long id);
}
