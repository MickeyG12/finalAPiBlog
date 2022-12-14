/**
 * 
 */
package com.fidelity.finalAPI.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fidelity.finalAPI.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	public Optional<Category> getByName(String name);
}
