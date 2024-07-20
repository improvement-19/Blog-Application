package com.suvankar.blogapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvankar.blogapis.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	
	
}
