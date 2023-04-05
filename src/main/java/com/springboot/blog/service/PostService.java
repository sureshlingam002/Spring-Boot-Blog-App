package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String orderBy);
	
	PostDto getPostById(long id);

	PostDto updatePostById(PostDto postDto, long id);
	
	void DeleteById(long id);

	List<PostDto> getPostsByCategory(Long categoryId);
}
