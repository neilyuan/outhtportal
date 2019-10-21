package com.mooc.oauth.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		return User
			.withUsername("admin")
			.password(passwordEncoder.encode("123456"))
			.authorities("ROLE_ADMIN")
			.build();
	}
}
