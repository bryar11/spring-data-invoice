package com.javausergroupcr.springdata.app.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.javausergroupcr.springdata.app.models.entity.Client;

public interface IClientService {

	public Page<Client> findAll(Pageable pageable);
	
	public Page<Client> findAllByName(String term, Pageable pageable);

	public Client findById(Long id);

	public Client fetchByIdWithInvoices(Long id);

	public void save(Client client);

}
