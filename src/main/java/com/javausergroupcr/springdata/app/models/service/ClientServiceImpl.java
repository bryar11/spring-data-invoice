package com.javausergroupcr.springdata.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.dao.IClientDao;
import com.javausergroupcr.springdata.app.models.entity.Client;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAllByEnabledTrueOrderByNameAsc(pageable);
	}

	@Override
	public Page<Client> findAllByName(String name, Pageable pageable) {
		return clientDao.findAllByNameContainingIgnoreCaseAndEnabledTrueOrderByNameAsc(name, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return clientDao.findByIdAndEnabledTrue(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client fetchByIdWithInvoices(Long id) {
		return clientDao.fetchByIdWithInvoices(id);
	}
	
	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

}
