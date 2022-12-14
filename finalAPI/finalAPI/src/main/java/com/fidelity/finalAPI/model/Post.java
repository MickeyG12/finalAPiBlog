/**
 * 
 */
package com.fidelity.finalAPI.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="POST")
public class Post {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="title")
	private String title;
	@Column(name="meta_title")
	private String metaTitle;
	@Column(name="slug")
	private String slug;
	@Column(name="summary")
	private String summary;
	@Column(name="published")
	private Boolean published;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="updated_at")
	private Date updatedAt;
	@Column(name="published_at")
	private Date publishedAt;
	//@Lob
	@Column(name="content")
	private String content;
	@Column(name="image_url")
	private String imageUrl;
	
	@ManyToOne(cascade = CascadeType.MERGE)	
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_post_user"))
	private User user;
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name="POSTS_BY_TAG", joinColumns = @JoinColumn(name="post_id"),
			inverseJoinColumns =  @JoinColumn(name="tag_id"))
	private List<Tag> tags = new ArrayList<>();
	@ManyToMany(cascade= CascadeType.MERGE)
	@JoinTable(name="POSTS_BY_CATEGORY", joinColumns = @JoinColumn(name="post_id"),
			inverseJoinColumns = @JoinColumn(name="category_id"))
	private List<Category> categories = new ArrayList<>();
	
	@OneToMany(mappedBy =  "post")
	private List<PostComment> comments;
	
	@OneToMany(mappedBy = "post")
	private List<PostInteraction> postInteractions;
		
	//private Long parentId;
	
	
	
	/**
	 * 
	 */
	public Post() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @param title
	 * @param metaTitle
	 * @param summary
	 * @param content
	 */
	public Post(String title, String metaTitle, String summary, String content) {
		super();
		this.title = title;
		this.metaTitle = metaTitle;
		this.summary = summary;
		this.content = content;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
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



	public String getSummary() {
		return summary;
	}



	public void setSummary(String summary) {
		this.summary = summary;
	}



	public Boolean getPublished() {
		return published;
	}



	public void setPublished(Boolean published) {
		this.published = published;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public Date getPublishedAt() {
		return publishedAt;
	}



	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getPosts().add(this);
	}
	
	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getPosts().remove(this);
	}
	
	public void addCategoy(Category category) {
		categories.add(category);
		category.getPosts().add(this);
	}
	
	public void removeCategory(Category category) {
		categories.remove(category);
		category.getPosts().remove(this);
	}



	public List<Tag> getTags() {
		return tags;
	}



	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}



	public List<Category> getCategories() {
		return categories;
	}



	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}



	public List<PostComment> getComments() {
		return comments;
	}



	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}
	
	

}
