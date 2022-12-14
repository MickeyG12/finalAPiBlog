/**
 * 
 */
package com.fidelity.finalAPI.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fidelity.finalAPI.errors.ValidateException;
import com.fidelity.finalAPI.model.Category;
import com.fidelity.finalAPI.model.Post;
import com.fidelity.finalAPI.model.PostComment;
import com.fidelity.finalAPI.model.PostInteraction;
import com.fidelity.finalAPI.model.Tag;
import com.fidelity.finalAPI.model.User;
import com.fidelity.finalAPI.model.dto.CategoryDto;
import com.fidelity.finalAPI.model.dto.MappingUtils;
import com.fidelity.finalAPI.model.dto.PostCommentDto;
import com.fidelity.finalAPI.model.dto.PostDto;
import com.fidelity.finalAPI.model.dto.PostInteractionDto;
import com.fidelity.finalAPI.model.dto.TagDto;
import com.fidelity.finalAPI.model.dto.UserDto;
import com.fidelity.finalAPI.model.flags.PostInterationFlags;
import com.fidelity.finalAPI.repositories.CategoryRepository;
import com.fidelity.finalAPI.repositories.PostCommentRepository;
import com.fidelity.finalAPI.repositories.PostInteractionRepository;
import com.fidelity.finalAPI.repositories.PostRepository;
import com.fidelity.finalAPI.repositories.TagRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository post_repository;
	
	@Autowired
	private TagRepository tag_repository;
	
	@Autowired
	private CategoryRepository category_repository;
	
	@Autowired
	private PostCommentRepository comment_repository;
	
	@Autowired
	private PostInteractionRepository post_interation_repository;
	
	/**
	 * 
	 */
	public PostService() {
		// TODO Auto-generated constructor stub
	}
	
	public List<PostDto> getAllPosts() {
		List<Post> findAll = (List<Post>) post_repository.findAll();
		
		List<PostDto> result = new ArrayList<>();
		Iterator<Post> itPosts = findAll.iterator();
		ModelMapper mapper = new ModelMapper();
		while (itPosts.hasNext()) {
			PostDto postItem = mapper.map(itPosts.next(), PostDto.class);
			
			result.add(postItem);
		}
		
		return result;
		
	}
	
	public List<PostDto> getAllPostsPublished() {
		
		List<Post> postFound = post_repository.getByPublished(true);		
		
		List<PostDto> result = new ArrayList<>();
				
		MappingUtils utils = new MappingUtils();
		result = utils.mapList(postFound, PostDto.class);
		
		return result;
	}
	
	public PostDto createPost(UserDto user, PostDto newPost) {
		//TODO Validar post con user
		
		ModelMapper mapper = new ModelMapper();
		Post post = mapper.map(newPost, Post.class);
				
		// Set post properties
		post.setPublished(true);
		post.setCreatedAt(new Date());
		post.setPublishedAt(new Date());
		post.setTags(new ArrayList<>());
		post.setCategories(new ArrayList<>());
		
		// Set list of tags
		if ((newPost.getTags() != null) && (!newPost.getTags().isEmpty())) {
			Iterator<TagDto> itTagsIDs = newPost.getTags().iterator();
			
			while (itTagsIDs.hasNext()) {
				TagDto currentTagDto = itTagsIDs.next();
				// Get tag from DB
				Optional<Tag> foundTag = tag_repository.findById(currentTagDto.getId());
				
				if (foundTag.isPresent()) {
					post.addTag(foundTag.get());
				} else {
					throw new ValidateException("No such tag found");
				}
			}
			
			
		}
		// Set list of categories
		if ((newPost.getCategories() != null) && (!newPost.getCategories().isEmpty())) {
			Iterator<CategoryDto> itCategoriesIDs = newPost.getCategories().iterator();
			
			while (itCategoriesIDs.hasNext()) {
				CategoryDto currentCategoryDto = itCategoriesIDs.next();
				// Get Category from DB
				Optional<Category> foundCategory = category_repository.findById(currentCategoryDto.getId());
				
				if (foundCategory.isPresent()) {
					post.addCategoy(foundCategory.get());
				} else {
					throw new ValidateException("No such category found");
				}
			}
		}
		
		this.post_repository.save(post);
		
		PostDto resul = mapper.map(post, PostDto.class);		
		return resul;
	}
	
	public PostDto modifyPost(UserDto user, Long id, PostDto modifyPost) {
		//TODO Validar post con user
		
		ModelMapper mapper = new ModelMapper();
		//Post post = mapper.map(modifyPost,  Post.class);
		Optional<Post> searchPost = post_repository.findById(id);
		
		Post updatePost = null;
		if (searchPost.isPresent()) {
			updatePost = searchPost.get();
			
			updatePost.setTitle(modifyPost.getTitle());
			updatePost.setMetaTitle(modifyPost.getMetaTitle());
			updatePost.setSummary(modifyPost.getSummary());
			updatePost.setContent(modifyPost.getContent());
			
			post_repository.save(updatePost);
		} else {
			throw new ValidateException("Invalid post ID");
		}
		
		PostDto resul = mapper.map(updatePost, PostDto.class);
		return resul;
	}
	
	public void deletePostByID(UserDto user, Long id) {
		post_repository.deleteById(id);	
	}
	
	
	public PostCommentDto createPostComment(UserDto user, PostCommentDto newPostComment) {
		//TODO Validar post con user
		
		ModelMapper mapper = new ModelMapper();
		PostComment postComment = mapper.map(newPostComment, PostComment.class);
		
		Optional<Post> postFound = post_repository.findById(newPostComment.getPost_id());
		//Optional<User> userFound = user_repository.findById(newPostComment.getUser_id());
		if (!postFound.isPresent()) {
			throw new ValidateException("No such psot found");
		} else {
			postComment.setPost(postFound.get());
		}
		
		this.comment_repository.save(postComment);
		
		PostCommentDto resul = mapper.map(postComment, PostCommentDto.class);
		return resul;
	}
	
	public PostCommentDto getPostComment(UserDto user, Long id) {
		Optional<PostComment> commentFound = comment_repository.findById(id);
		
		if (commentFound.isPresent()) {
			ModelMapper mapper = new ModelMapper();
			PostCommentDto postCommentDto = mapper.map(commentFound.get(), PostCommentDto.class);
			
			return postCommentDto;
		} else {
			throw new ValidateException("No comment found");
		}
	}
	
	public PostCommentDto modifyPostComment(UserDto user, Long id, PostCommentDto modifyComment) {
		ModelMapper mapper = new ModelMapper();
		Optional<PostComment> searchPostComment = comment_repository.findById(id);
		
		PostComment updateComment = null;
		if (searchPostComment.isPresent()) {
			updateComment = searchPostComment.get();
			
			updateComment.setTitle(modifyComment.getTitle());
			updateComment.setContent(modifyComment.getContent());
			updateComment.setPublishedAt(new Date());
			
			comment_repository.save(updateComment);
		} else {
			throw new ValidateException("Invalid post comment ID");
		}
		
		PostCommentDto resul = mapper.map(updateComment, PostCommentDto.class);
		return resul;
	}
	
	public void deletePostCommentByID(UserDto user, Long id) {
		comment_repository.deleteById(id);
	}
	
	
	
	
	public PostInteractionDto doPostLike(UserDto user, Long postID) {
		Optional<Post> postFound = post_repository.findById(postID);
		
		if (!postFound.isPresent()) {
			throw new ValidateException("Invalid post ID");
		}
		
		PostInteraction postLike = new PostInteraction();
		
		postLike.setFlags(PostInterationFlags.LIKE);
		postLike.setPost(postFound.get());
		
		post_interation_repository.save(postLike);
		
		ModelMapper mapper = new ModelMapper();
		PostInteractionDto resul = mapper.map(postLike, PostInteractionDto.class);
		resul.setPost_id(postFound.get().getId());
		return resul;
	}
	
	public PostInteractionDto doPostDislike(UserDto user, Long postID) {
		Optional<Post> postFound = post_repository.findById(postID);
		
		if (!postFound.isPresent()) {
			throw new ValidateException("Invalid post ID");
		}
		
		
		PostInteraction postDislike = new PostInteraction();
		
		postDislike.setFlags(PostInterationFlags.DISLIKE);
		postDislike.setPost(postFound.get());
		
		post_interation_repository.save(postDislike);
		
		ModelMapper mapper = new ModelMapper();
		PostInteractionDto resul = mapper.map(postDislike, PostInteractionDto.class);
		resul.setPost_id(postFound.get().getId());
		return resul;
	}
	
	public PostDto getPostByID(Long id) {
		Optional<Post> postFound = post_repository.findById(id);
		
		if (postFound.get() != null) {
			ModelMapper mapper = new ModelMapper();
			PostDto postDTO = mapper.map(postFound.get(), PostDto.class);
			
			return postDTO;
		} else {
			throw new ValidateException("No post found");
		}
		
	}
	
	public List<PostDto> getPostByTitle(String title) {
		
		List<Post> postList = post_repository.getByTitle(title);
		
		List<PostDto> result = new ArrayList<>();
		
		MappingUtils utils = new MappingUtils();
		result = utils.mapList(postList, PostDto.class);
		
		return result;
	}
	
	public List<PostDto> getPostsByTag(String tag) {
		Optional<Tag> tagFound = tag_repository.getByName(tag);
				
		if (!tagFound.isPresent()) {
			throw new ValidateException("Invalid tag");
		}
		
		List<Post> postList = post_repository.findPostsByTag(tag);
		
		List<PostDto> result = new ArrayList<>();
		MappingUtils utils = new MappingUtils();
		result = utils.mapList(postList, PostDto.class);
		return result;
	}
	
	public List<PostDto> getPostsByCategory(String category) {
		Optional<Category> categoryFound = category_repository.getByName(category);
		
		if (!categoryFound.isPresent()) {
			throw new ValidateException("Invalid category");
		}
		
		List<Post> postList = post_repository.findPostsByCategory(category);
		
		List<PostDto> result = new ArrayList<>();
		MappingUtils utils = new MappingUtils();
		result = utils.mapList(postList, PostDto.class);
		return result;
		
	}
	

}
