package com.javausergroupcr.springdata.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.dao.IPaymentDao;
import com.javausergroupcr.springdata.app.models.entity.Payment;

@Service
public class PaymentServiceImpl implements IPaymentService {

	@Autowired
	private IPaymentDao paymentDao;

	@Override
	@Transactional
	public void save(Payment payment) {
		paymentDao.save(payment);
	}

	@Override
	public Payment findById(long id) {
		return paymentDao.findByIdAndEnabledTrue(id);
	}

}
