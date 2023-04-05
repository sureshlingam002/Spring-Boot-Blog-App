package com.springboot.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private CategoryRepository categoryRepository;
	private ModelMapper mapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository,ModelMapper mapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.mapper=mapper;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		
		Category category = categoryDtoToCategory(categoryDto);
		
		Category categoryResponse = categoryRepository.save(category);
		
		CategoryDto cdto = categoryToCategoryDto(categoryResponse);
		
		return cdto;
		
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto)
	{
		return mapper.map(categoryDto, Category.class);
	}
	
	public CategoryDto categoryToCategoryDto(Category category)
	{
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDto> cdto = new ArrayList<>();
		
		for(int i=0;i<categories.size();i++)
		{
			cdto.add(categoryToCategoryDto(categories.get(i)));
		}
		
		return cdto;
	}

	@Override
	public CategoryDto getCategoryById(long id) {
		
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
		
		return categoryToCategoryDto(category);
	}

	@Override
	public CategoryDto updateCategoryById(CategoryDto categoryDto, long id) {
		
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
		
		category.setDescription(categoryDto.getDescription());
		category.setName(categoryDto.getName());
		category.setId(id);
		
		categoryRepository.save(category);
		
		return categoryToCategoryDto(category);
	}
	
	@Override
	public String deleteCategoryById(long id) {
		
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
		categoryRepository.delete(category);
		
		return "The Category with the ID :"+id+" has been deleted successfully";
	}

}
