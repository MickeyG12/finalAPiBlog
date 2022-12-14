/**
 * 
 */
package com.fidelity.finalAPI.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="TAG")
public class Tag {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="meta_title")
	private String metaTitle;
	@Column(name="slug")
	private String slug;
	//@Lob
	@Column(name="description")
	private String description;
	
	@ManyToMany(mappedBy = "tags")
	private List<Post> posts = new ArrayList<>();
	/**
	 * 
	 */
	public Tag() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * @param title
	 * @param metaTitle
	 * @param slug
	 * @param content
	 */
	public Tag(String name, String metaTitle, String slug, String description) {
		super();
		this.name = name;
		this.metaTitle = metaTitle;
		this.slug = slug;
		this.description = description;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMetaTitle() {
		return metaTitle;
	}
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
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



	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}	
	

}
