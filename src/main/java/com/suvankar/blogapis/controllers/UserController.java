package com.suvankar.blogapis.controllers;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suvankar.blogapis.payloads.ApiResponse;
import com.suvankar.blogapis.payloads.UserDto;
import com.suvankar.blogapis.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")

public class UserController {
	@Autowired
    private UserService userService;
    
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
    	UserDto createUserDto=this.userService.createUser(userDto);
    	return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
    }
    
    
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid)
    {
    	UserDto updatedUser=this.userService.updateUser(userDto, uid);
    	return  ResponseEntity.ok(updatedUser);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable("userId") Integer uid)
    {
    	this.userService.deleteUser(uid);
    	return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
    	return ResponseEntity.ok(this.userService.getAllUser());
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
    {
    	return ResponseEntity.ok(this.userService.getById(userId));
    }
    
}
