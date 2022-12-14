/**
 * 
 */
package com.fidelity.finalAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fidelitytechnologies.training.blogapp.model.dto.CategoryDto;
import com.fidelitytechnologies.training.blogapp.model.dto.PostCommentDto;
import com.fidelitytechnologies.training.blogapp.model.dto.PostDto;
import com.fidelitytechnologies.training.blogapp.model.dto.PostInteractionDto;
import com.fidelitytechnologies.training.blogapp.model.dto.TagDto;
import com.fidelitytechnologies.training.blogapp.model.dto.UserDto;
import com.fidelitytechnologies.training.blogapp.services.CategoryService;
import com.fidelitytechnologies.training.blogapp.services.PostService;
import com.fidelitytechnologies.training.blogapp.services.TagService;
import com.fidelitytechnologies.training.blogapp.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/blog_manager_area")
public class PostController {

	@Autowired
	private UserService userService; 
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 
	 */
	public PostController() {
		// TODO Auto-generated constructor stub
	}
	
	@ApiOperation(value = "Obtener Perfil de Usuario Registrado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message="OK. El recurso es obtenido"),
			@ApiResponse(code = 204, message="Error. El usuario no esta registrado")
	})
	@GetMapping("/getProfile/{id}")
	public ResponseEntity<UserDto> getUserByID(@PathVariable("id") Long id) {
		UserDto user = userService.getUserProfile(id);
		
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User was not found!");
		} else {
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Registrar un nuevo post en Blog")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message="CREATED. El posteo fue registrado y publicado", response = PostDto.class),
			@ApiResponse(code = 404, message="Error al crear nuevo posteo")
	})
	@PostMapping("/post/create")
	public ResponseEntity<PostDto> createPost(
			@ApiParam(value = "Nuevo post. Categories y Tags solo es necesario especificar el ID") 
			@RequestBody PostDto post) {
		post = postService.createPost(null, post);
		
		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Obtener post en Blog por ID")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message="OK. El posteo fue encontrado", response = PostDto.class),
			@ApiResponse(code = 404, message="Error al obtener nuevo posteo")
	})
	@GetMapping("/post/get/{id}")
	public ResponseEntity<PostDto> getPostSimple(@PathVariable("id") Long id) {
		return new ResponseEntity<PostDto>(postService.getPostByID(id), HttpStatus.OK);		
	}
	
	@ApiOperation(value = "Modificar un post en Blog")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message="OK. El posteo fue modificado", response = PostDto.class),
			@ApiResponse(code = 404, message="Error al modificar posteo")
	})
	@PutMapping("/post/edit/{id}")
	public ResponseEntity<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDetail) {
		postDetail = postService.modifyPost(null, id, postDetail);
	
		return new ResponseEntity<PostDto>(postDetail, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Eliminar un post en Blog")
	@DeleteMapping("/post/delete/{id}")
	public String deletePost(@PathVariable("id") Long id) {
		postService.deletePostByID(null, id);
		return "Delete OK";
	}
	
	@ApiOperation(value = "Busqueda de Post por Tag")
	@GetMapping("/post/search_by_tag/{tag}")
	public List<PostDto> searchPostByTag(@PathVariable String tag) {
		return postService.getPostsByTag(tag);
	}
	
	@ApiOperation(value = "Busqueda de Post por Categoria")
	@GetMapping("/post/search_by_category/{category}")
	public List<PostDto> searchPostByCategory(@PathVariable String category) {
		return postService.getPostsByCategory(category);
	}
	
	@ApiOperation(value = "Registrar un nuevo comentario a un Post")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message="CREATED. El comentario fue registrado y publicado", response = PostCommentDto.class),
			@ApiResponse(code = 404, message="Error al crear nuevo comentario")
	})
	@PostMapping("/post/comment/create")
	public ResponseEntity<PostCommentDto> createPostComment(@RequestBody PostCommentDto postComment) {
		postComment = postService.createPostComment(null, postComment);
				
		return new ResponseEntity<PostCommentDto>(postComment, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Crear nuevo Comentario a un Post")
	@GetMapping("/post/comment/get/{id}")
	public ResponseEntity<PostCommentDto> getPostCommentByID(@PathVariable("id") Long id) {
		return new ResponseEntity<PostCommentDto>(postService.getPostComment(null, id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Modificar Comentario de un Post")
	@PutMapping("/post/comment/edit/{id}")
	public ResponseEntity<PostCommentDto> editPostComment(@PathVariable Long id, @RequestBody PostCommentDto commentDetail) {
		commentDetail = postService.modifyPostComment(null, id, commentDetail);
		
		return new ResponseEntity<PostCommentDto>(commentDetail, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Borrar un comentario")
	@DeleteMapping("/post/comment/delete/{id}")
	public String deleteComment(@PathVariable("id") Long id) {
		postService.deletePostCommentByID(null, id);
		return "Delete OK";
	}
	
	
	@PutMapping("/post/like/{id}")
	public ResponseEntity<PostInteractionDto> editPostLike(@PathVariable Long id) {
		return new ResponseEntity<PostInteractionDto>(postService.doPostLike(null, id), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/post/dislike/{id}")
	public ResponseEntity<PostInteractionDto> editPostDislike(@PathVariable Long id) {
		return new ResponseEntity<PostInteractionDto>(postService.doPostDislike(null, id), HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "Registrar un nuevo Tag")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message="CREATED. El tag fue registrado", response = TagDto.class),
			@ApiResponse(code = 404, message="Error al crear nuevo tag")
	})
	@PostMapping("/tag/create")
	public ResponseEntity<TagDto> createTag(@RequestBody TagDto tag) {
		tag = tagService.createTag(null, tag);
		
		return new ResponseEntity<TagDto>(tag, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Eliminar un Tag en Blog")
	@DeleteMapping("/tag/delete/{id}")
	public String deleteTag(@PathVariable("id") Long id) {
		tagService.deleteTagByID(null, id);
		return "Delete OK";
	}
	
	@ApiOperation(value = "Registrar una nueva Categoria")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message="CREATED. La categoria fue registrada", response = CategoryDto.class),
			@ApiResponse(code = 404, message="Error al crear nueva categoria")
	})
	@PostMapping("/category/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category) {
		category = categoryService.createCategory(null, category);
		
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Eliminar una Categoria en Blog")
	@DeleteMapping("/category/delete/{id}")
	public String deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteCategoryByID(null, id);
		
		return "Delete OK";
	}

}
