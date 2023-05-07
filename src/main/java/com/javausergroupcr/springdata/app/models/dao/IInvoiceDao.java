package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.Client;
import com.javausergroupcr.springdata.app.models.entity.Invoice;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

	@Query("select f from Invoice f join fetch f.client c join fetch f.items l join fetch l.product where f.id=?1 and f.enabled=true")
	public Invoice fetchByIdWithClientWithItemWithProduct(Long id);
	
	public Invoice findByIdAndEnabledTrue(Long id);
	
	@Query("select f from Invoice f join f.client c where c.id=?1 and f.enabled=true order by f.id desc")
	public Page<Invoice> findByClientAndEnabledTrueOrderByIdDesc(Long clientId, Pageable pageable);
	
	@Query("select coalesce(sum(i.quantity * i.price),0) from Invoice f join f.items i where f.client=?1 and f.enabled=true")
	public double calculateTotalByClient(Client client);
	
	@Query("select coalesce(sum(p.amount),0) from Invoice f join f.payments p where f.client=?1 and f.enabled=true and p.enabled=true")
	public double calculatePaidByClient(Client client);
}
