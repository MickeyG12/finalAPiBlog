/**
 * 
 */
package com.fidelity.finalAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelitytechnologies.training.blogapp.model.dto.CategoryDto;
import com.fidelitytechnologies.training.blogapp.model.dto.PostDto;
import com.fidelitytechnologies.training.blogapp.services.PostService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class WebController {

	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		//model
		
		return "redirect:/index";
	}
	
	@ApiOperation(value = "Web. Pagina index")	
	@ApiResponse(code = 200, message="Template Index")
	@GetMapping("/index")
	public String index(Model model) {
		
		List<PostDto> postList = postService.getAllPostsPublished();
		
		model.addAttribute("posts", postList);
		return "index";
	}
		
	@GetMapping("/post/getAll") 
	public List<PostDto> getAllPosts() {	
		
		return postService.getAllPostsPublished();
	}	
	

}
