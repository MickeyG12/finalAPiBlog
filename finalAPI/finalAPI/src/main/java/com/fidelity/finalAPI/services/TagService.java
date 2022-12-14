/**
 * 
 */
package com.fidelity.finalAPI.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.finalAPI.model.Tag;
import com.fidelity.finalAPI.model.dto.TagDto;
import com.fidelity.finalAPI.model.dto.UserDto;
import com.fidelity.finalAPI.repositories.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository tag_repository;
	
	/**
	 * 
	 */
	public TagService() {
		// TODO Auto-generated constructor stub
	}
	
	public TagDto createTag(UserDto user, TagDto newTag) {
		ModelMapper mapper = new ModelMapper();
		Tag tag = mapper.map(newTag, Tag.class);
		
		this.tag_repository.save(tag);
		
		TagDto resul = mapper.map(tag, TagDto.class);
		return resul;
	}
	
	public void deleteTagByID(UserDto user, Long id) {
		tag_repository.deleteById(id);
	}

}
