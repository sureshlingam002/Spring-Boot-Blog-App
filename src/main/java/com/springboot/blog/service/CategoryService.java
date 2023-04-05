package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto getCategoryById(long id);
	
	CategoryDto updateCategoryById(CategoryDto categoryDto, long id);
	
	String deleteCategoryById(long id);
	

}
