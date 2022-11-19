package com.javausergroupcr.springdata.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.dao.IInvoiceDao;
import com.javausergroupcr.springdata.app.models.entity.Invoice;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	@Override
	@Transactional
	public void save(Invoice invoice) {
		invoiceDao.save(invoice);
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice findById(Long id) {
		return invoiceDao.findByIdAndEnabledTrue(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id) {
		Invoice invoice = invoiceDao.fetchByIdWithClientWithItemInvoiceWithProduct(id);
		return invoice;
	}

}
