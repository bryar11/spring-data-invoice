package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.javausergroupcr.springdata.app.models.entity.Client;

public interface IClientDao extends PagingAndSortingRepository<Client, Long> {

	@Query("select c from Client c left join fetch c.invoices f where c.id=?1")
	public Client fetchByIdWithInvoices(Long id);
}
