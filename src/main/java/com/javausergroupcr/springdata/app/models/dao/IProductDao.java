package com.javausergroupcr.springdata.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

	public Page<Product> findAllByEnabledTrueOrderByNameAsc(Pageable pageable);

	@Query("select p from Product p where (lower(p.code) like lower(?1) or lower(p.name) like lower(?1)) and p.enabled=true order by p.name asc")
	public List<Product> findAllByTerm(String term);

	@Query("select p from Product p where (lower(p.code) like lower(?1) or lower(p.name) like lower(?1)) and p.enabled=true order by p.name asc")
	public Page<Product> findAllByTerm(String name, Pageable pageable);
	
	public Product findByIdAndEnabledTrue(Long id);
}
