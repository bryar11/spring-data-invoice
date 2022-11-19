package com.javausergroupcr.springdata.app.models.service;

import com.javausergroupcr.springdata.app.models.entity.Payment;

public interface IPaymentService {

	public void save(Payment payment);

	public Payment findById(long paymentId);

}
