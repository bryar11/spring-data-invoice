package com.javausergroupcr.springdata.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.javausergroupcr.springdata.app.models.entity.Client;
import com.javausergroupcr.springdata.app.models.entity.Invoice;
import com.javausergroupcr.springdata.app.models.entity.Product;

public interface IClientService {

	public List<Client> findAll();

	public Page<Client> findAll(Pageable pageable);

	public void save(Client client);

	public Client findOne(Long id);

	public Client fetchByIdWithInvoices(Long id);

	public void delete(Long id);

	public List<Product> findByName(String term);

	public void saveInvoice(Invoice invoice);

	public Product findProductById(Long id);

	public Invoice findInvoiceById(Long id);

	public void deleteInvoice(Long id);

	public Invoice fetchInvoiceByIdWithClientWithItemInvoiceWithProduct(Long id);

}
