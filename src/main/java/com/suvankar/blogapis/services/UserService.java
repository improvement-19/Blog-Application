package com.suvankar.blogapis.services;

import java.util.List;

import com.suvankar.blogapis.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer id);
	UserDto getById(Integer id);
	List<UserDto> getAllUser();
	void deleteUser(Integer id);
	
}
