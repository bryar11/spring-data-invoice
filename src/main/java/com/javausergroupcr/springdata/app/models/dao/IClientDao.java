package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.javausergroupcr.springdata.app.models.entity.Client;

public interface IClientDao extends PagingAndSortingRepository<Client, Long> {

	@Query("select c from Client c left join fetch c.invoices i where c.id=?1 and c.enabled=true")
	public Client fetchByIdWithInvoices(Long id);
	
	public Client findByIdAndEnabledTrue(Long id);

	public Page<Client> findAllByEnabledTrueOrderByNameAsc(Pageable pageable);

	public Page<Client> findAllByNameContainingIgnoreCaseAndEnabledTrueOrderByNameAsc(String name, Pageable pageable);
}
