package com.javausergroupcr.springdata.app.models.service;

import com.javausergroupcr.springdata.app.models.entity.DBUser;

public interface IUserService {

	public DBUser findByUsername(String username);

}
