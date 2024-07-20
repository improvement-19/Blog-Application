/**
 * 
 */
package com.suvankar.blogapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suvankar.blogapis.entity.Category;
import com.suvankar.blogapis.exceptions.ResultNotFoundException;
import com.suvankar.blogapis.payloads.CategoryDto;
import com.suvankar.blogapis.repositories.CategoryRepo;
import com.suvankar.blogapis.services.CategoryService;

/**
 * 
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResultNotFoundException("Category","categoryId" , categoryId));
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		Category saved=this.categoryRepo.save(cat);
		return this.modelMapper.map(saved, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer CategoryId) {
		Category cat=this.categoryRepo.findById(CategoryId).orElseThrow(()->new ResultNotFoundException("Category", "categoryId", CategoryId));
		this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResultNotFoundException("Category", "catogoryId", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> catDtos= categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

}
