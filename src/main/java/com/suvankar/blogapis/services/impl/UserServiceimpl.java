package com.suvankar.blogapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.suvankar.blogapis.config.AppConstants;
import com.suvankar.blogapis.entity.Role;
import com.suvankar.blogapis.entity.User;
import com.suvankar.blogapis.exceptions.ResultNotFoundException;
import com.suvankar.blogapis.payloads.UserDto;
import com.suvankar.blogapis.repositories.RoleRepo;
import com.suvankar.blogapis.repositories.UserRepo;
import com.suvankar.blogapis.services.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepo userRepo ;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User saveduser=dtoToUser(userDto);
		saveduser.setPassword(this.passwordEncoder.encode(saveduser.getPassword()));
		System.out.println(saveduser.getEmail());
		this.userRepo.save(saveduser);
		return this.userToDto(saveduser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		
		User user=this.userRepo.findById(id).orElseThrow(()->new ResultNotFoundException("User","Id",id));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setId(userDto.getId());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User updateduser=this.userRepo.save(user);
		UserDto udto=this.userToDto(updateduser);
		return udto;
	}

	@Override
	public UserDto getById(Integer id) {
		//this is to instruct database ,userRepo is database object 
		User user=this.userRepo.findById(id).orElseThrow(()->new ResultNotFoundException("User", "Id", id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.userRepo.findAll();
		List<UserDto> userdtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userdtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(()->new ResultNotFoundException("User", "Id", id));
		this.userRepo.delete(user);

	}
	
	public User dtoToUser(UserDto userDto)
	{
		User user =this.modelMapper.map(userDto, User.class);
		
//		User user =new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setAbout(userDto.getAbout());
//		
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	public UserDto userToDto(User user)
	{
		UserDto udto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto udto=new UserDto();
//		udto.setAbout(user.getAbout());
//		udto.setEmail(user.getEmail());
//		udto.setId(user.getId());
//		udto.setName(user.getName());
//		udto.setPassword(user.getPassword());
		
		return udto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto,User.class );
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role= this.roleRepo.findById(AppConstants.ROLE_NORMAL).get();
		user.getRoles().add(role);
		User newUser=this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
