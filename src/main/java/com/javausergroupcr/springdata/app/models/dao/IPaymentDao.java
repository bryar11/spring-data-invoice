package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.Payment;

public interface IPaymentDao extends CrudRepository<Payment, Long> {
	
	Payment findByIdAndEnabledTrue(Long id);
}
