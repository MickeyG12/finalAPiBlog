/**
 * 
 */
package com.fidelity.finalAPI.model.dto;

import org.modelmapper.ModelMapper;

public interface FactoryDTO {

	default ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils utils) {
		return mapper;
	}
}
