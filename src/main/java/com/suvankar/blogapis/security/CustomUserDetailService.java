package com.suvankar.blogapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suvankar.blogapis.entity.User;
import com.suvankar.blogapis.exceptions.ResultNotFoundException;
import com.suvankar.blogapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= this.userRepo.findByEmail(username).orElseThrow(()->new ResultNotFoundException("User", "email :"+ username , 0));
		return user;
	}
	
	
}
