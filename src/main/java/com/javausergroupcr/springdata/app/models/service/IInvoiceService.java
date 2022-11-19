package com.javausergroupcr.springdata.app.models.service;

import com.javausergroupcr.springdata.app.models.entity.Invoice;

public interface IInvoiceService {

	public void save(Invoice invoice);

	public Invoice findById(Long id);

	public Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id);

}
