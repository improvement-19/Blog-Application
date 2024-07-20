package com.suvankar.blogapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvankar.blogapis.entity.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer >{
	
}
