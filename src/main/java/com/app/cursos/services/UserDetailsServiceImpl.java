package com.app.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.cursos.entity.Users;
import com.app.cursos.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository  userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Users user = userRepository.getUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
			
		}
		
		MyUserDetails my = new MyUserDetails(user);
		return my;
		
	}
}
