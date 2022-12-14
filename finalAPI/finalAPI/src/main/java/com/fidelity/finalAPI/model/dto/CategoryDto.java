/**
 * 
 */
package com.fidelity.finalAPI.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class CategoryDto implements FactoryDTO {

	@ApiModelProperty(required = true)
	private Long id;
	@ApiModelProperty(required = false)
	private String name;
	@ApiModelProperty(required = false)
	private String description;
	/**
	 * 
	 */
	public CategoryDto() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
}
