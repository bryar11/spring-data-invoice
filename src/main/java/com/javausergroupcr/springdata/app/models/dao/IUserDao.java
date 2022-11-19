package com.javausergroupcr.springdata.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.javausergroupcr.springdata.app.models.entity.DBUser;

public interface IUserDao extends CrudRepository<DBUser, Long> {

	public DBUser findByUsernameAndEnabledTrue(String username);
}
