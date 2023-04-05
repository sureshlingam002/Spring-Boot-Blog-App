package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name = "CRUD REST APIs for Post Resource"
		)
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@Operation(summary ="Create Post REST API", 
			description = "Create Post REST API is used to create the resources in the database")
	@ApiResponse(responseCode="201",
			description = "Http Status 201 Created"	
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postdto)
	{
		return new ResponseEntity<>(postService.createPost(postdto), HttpStatus.CREATED);
	}
	
	
	
	@Operation(summary ="Get ALL Post REST API", 
			description = "Get all the resources in the database")
	@ApiResponse(responseCode="200",
			description = "Http Status 200 OK"	
			)
	@GetMapping
	public PostResponse GetAllPost(@RequestParam (value="pageSize", defaultValue=AppConstants.DEFAULT_PAGESIZE, required=false) int pageSize,
			@RequestParam (value="pageNo", defaultValue=AppConstants.DEFAULT_PAGENO, required=false)int pageNo,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_SORTBY, required=false) String sortBy,
			@RequestParam(value="orderBy", defaultValue=AppConstants.DEFAULT_ORDERBY, required=false) String orderBy
			)
	{
		return postService.getAllPost(pageNo, pageSize,sortBy,orderBy);
	}
	
	
	
	@Operation(summary ="Get Single Post By ID", 
			description = "Get single post from the database using the ID")
	@ApiResponse(responseCode="200",
			description = "Http Status 200 Success"	
			)
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") long id)
	{
		return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
	}
	
	
	
	@Operation(summary ="Update Post REST API", 
			description = "Update Post REST API is used to Update the resources in the database")
	@ApiResponse(responseCode="200",
			description = "Http Status 200 Ok"	
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id )
	{
		return new ResponseEntity<>(postService.updatePostById(postDto, id), HttpStatus.OK);
	}
	
	
	@Operation(summary ="Delete Post REST API", 
			description = "Delete the resources from the database")
	@ApiResponse(responseCode="200",
			description = "Http Status 200 OK"	
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> DeleteById(@PathVariable long id)
	{
		postService.DeleteById(id);
		return new ResponseEntity<String>("Post "+id+" has been deleted successfully", HttpStatus.OK);
	}
	
	
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable (name="id") Long categoryId)
	{
		return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
	}

}
