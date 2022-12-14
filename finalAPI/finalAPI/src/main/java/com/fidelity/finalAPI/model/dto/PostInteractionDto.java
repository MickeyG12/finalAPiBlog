/**
 * 
 */
package com.fidelity.finalAPI.model.dto;

public class PostInteractionDto implements FactoryDTO {

	private Long id;
	private Integer flags;
	
	private Long post_id;
	
	/**
	 * 
	 */
	public PostInteractionDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFlags() {
		return flags;
	}

	public void setFlags(Integer flags) {
		this.flags = flags;
	}

	public Long getPost_id() {
		return post_id;
	}

	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}

	
}
