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
	@Transactional(readOnly = true)
	public Page<Product> findAllByTerm(String term, Pageable pageable) {
		return productDao.findAllByTerm("%" + term + "%", pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAllByTerm(String term) {
		return productDao.findAllByTerm("%" + term + "%");
	}

	@Override
	@Transactional
	public Product findById(Long id) {
		return productDao.findByIdAndEnabledTrue(id);
	}
	
	@Override
	@Transactional
	public void save(Product product) {
		productDao.save(product);
	}
}
