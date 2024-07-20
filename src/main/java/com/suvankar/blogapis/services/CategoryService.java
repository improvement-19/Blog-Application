package com.suvankar.blogapis.services;

import java.util.List;

import com.suvankar.blogapis.payloads.CategoryDto;

public interface CategoryService {
	
	//create 
	public CategoryDto createCategory(CategoryDto categoryDto);
	//update 
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	//delete
	public void deleteCategory(Integer CategoryId);
	//get
	public CategoryDto getCategory(Integer categoryId);
	//get all
	public List<CategoryDto> getCategories();
	
}
