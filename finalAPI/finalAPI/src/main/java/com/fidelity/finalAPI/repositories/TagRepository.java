/**
 * 
 */
package com.fidelity.finalAPI.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fidelity.finalAPI.model.Tag;


public interface TagRepository extends CrudRepository<Tag, Long> {

	public Optional<Tag> getByName(String name);	
}
