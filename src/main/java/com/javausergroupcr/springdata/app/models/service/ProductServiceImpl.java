package com.javausergroupcr.springdata.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.dao.IProductDao;
import com.javausergroupcr.springdata.app.models.entity.Product;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Product> findAll(Pageable pageable) {
		return productDao.findAllByEnabledTrueOrderByNameAsc(pageable);
	}

	@Override
	public Page<Product> findAllByName(String name, Pageable pageable) {
		return productDao.findAllByNameContainingIgnoreCaseAndEnabledTrueOrderByNameAsc(name, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAllByName(String term) {
		return productDao.findByNameContainingIgnoreCaseAndEnabledTrue(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDao.findByIdAndEnabledTrue(id);
	}
	
	@Override
	@Transactional
	public void save(Product product) {
		productDao.save(product);
	}
}
