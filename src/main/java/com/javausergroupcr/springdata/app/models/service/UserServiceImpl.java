package com.javausergroupcr.springdata.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javausergroupcr.springdata.app.models.dao.IUserDao;
import com.javausergroupcr.springdata.app.models.entity.DBUser;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	public DBUser findByUsername(String username) {
		return userDao.findByUsernameAndEnabledTrue(username);
	}

}
