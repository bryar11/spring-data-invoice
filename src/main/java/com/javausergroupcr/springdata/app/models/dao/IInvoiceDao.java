package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.Invoice;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

	@Query("select f from Invoice f join fetch f.client c join fetch f.items l join fetch l.product where f.id=?1")
	public Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id);
}
