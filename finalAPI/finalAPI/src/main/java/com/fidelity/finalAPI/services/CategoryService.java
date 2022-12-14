/**
 * 
 */
package com.fidelity.finalAPI.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelitytechnologies.training.blogapp.model.Category;
import com.fidelitytechnologies.training.blogapp.model.dto.CategoryDto;
import com.fidelitytechnologies.training.blogapp.model.dto.UserDto;
import com.fidelitytechnologies.training.blogapp.repositories.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepository category_repository;
	
	/**
	 * 
	 */
	public CategoryService() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryDto createCategory(UserDto user, CategoryDto newCategory) {
		ModelMapper mapper = new ModelMapper();
		Category category = mapper.map(newCategory, Category.class);
		
		this.category_repository.save(category);
		
		CategoryDto resul = mapper.map(category, CategoryDto.class);
		return resul;
	}
	
	public void deleteCategoryByID(UserDto user, Long id) {
		category_repository.deleteById(id);
	}

}
