package com.javausergroupcr.springdata.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javausergroupcr.springdata.app.models.entity.DBAuthority;
import com.javausergroupcr.springdata.app.models.entity.DBUser;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		DBUser dbUser = userService.findByUsername(username);

		if (null == dbUser) {
			throw new UsernameNotFoundException("Usuario: " + username + " no existe en el sistema");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (DBAuthority dbAuthority : dbUser.getAuthorities()) {
			authorities.add(new SimpleGrantedAuthority(dbAuthority.getAuthority()));
		}

		if (authorities.isEmpty()) {
			throw new UsernameNotFoundException("Usuario '" + username + "' no tiene roles asignados");
		}

		return new User(dbUser.getName(), dbUser.getPassword(), dbUser.isEnabled(), true, true, true, authorities);
	}

}
