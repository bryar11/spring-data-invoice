package com.javausergroupcr.springdata.app.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.javausergroupcr.springdata.app.models.entity.Client;
import com.javausergroupcr.springdata.app.models.entity.Invoice;

public interface IInvoiceService {

	public void save(Invoice invoice);

	public Invoice findById(Long id);

	public Invoice fetchByIdWithClientWithItemWithProduct(Long id);
	
	public Page<Invoice> findByClientId(Long clientId, Pageable pageable);
	
	public double calculateBalanceByClient(Client client);

}
