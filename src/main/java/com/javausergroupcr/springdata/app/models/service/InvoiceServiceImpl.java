package com.javausergroupcr.springdata.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.dao.IInvoiceDao;
import com.javausergroupcr.springdata.app.models.entity.Client;
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
	@Transactional
	public Invoice findById(Long id) {
		return invoiceDao.findByIdAndEnabledTrue(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice fetchByIdWithClientWithItemWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithItemWithProduct(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Invoice> findByClientId(Long clientId, Pageable pageable) {
		return invoiceDao.findByClientAndEnabledTrueOrderByIdDesc(clientId, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public double calculateBalanceByClient(Client client) {
		double total = invoiceDao.calculateTotalByClient(client);
		double paid = invoiceDao.calculatePaidByClient(client);
		return total - paid;
	}

}
