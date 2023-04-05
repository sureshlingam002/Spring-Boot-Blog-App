package com.springboot.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;
	
	private ModelMapper modelmapper;
	
	private CategoryRepository categoryRepository;
		
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper,CategoryRepository categoryRepository) {
		super();
		this.postRepository = postRepository;
		this.modelmapper=mapper;
		this.categoryRepository=categoryRepository;
	}


	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
						.orElseThrow(()->new ResourceNotFoundException("Category", "Id", postDto.getCategoryId()));
		
		Post post = postDtoToPost(postDto);	
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = new PostDto();
		postResponse.setId(newPost.getId());
		postResponse.setTitle(newPost.getTitle());;
		postResponse.setDescription(newPost.getDescription());
		postResponse.setContent(newPost.getContent());
		postResponse.setCategoryId(newPost.getCategory().getId());
		
		return postResponse;
	}
	
	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String orderBy)
	{
		Sort sort = null;
		if(orderBy.equalsIgnoreCase(Sort.Direction.DESC.name()))
		{
			sort = Sort.by(sortBy).descending();
		}
		else
		{
			sort= Sort.by(sortBy).ascending();
		}
		
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> allposts = posts.getContent();
		
		
		List<PostDto> allpostDtoObjects = new ArrayList<>();
		
		for(int i=0;i<allposts.size();i++)
		{	
			allpostDtoObjects.add(postToPostDto(allposts.get(i)));
		}
		
		PostResponse response = new PostResponse();
		response.setContent(allpostDtoObjects);
		response.setPageNo(pageNo);
		response.setPageSize(pageSize);
		response.setTotalElements(posts.getTotalElements());
		response.setTotalPages(posts.getTotalPages());
		response.setLastPage(posts.isLast());
		
		return response;
	}
	
	public PostDto postToPostDto(Post post)
	{
		PostDto postDto= modelmapper.map(post, PostDto.class);
		/*
		 * PostDto pdto = new PostDto(); 
		 * pdto.setId(post.getId());
		 * pdto.setTitle(post.getTitle()); 
		 * pdto.setDescription(post.getDescription());
		 * pdto.setContent(post.getContent());
		 */
		return postDto;
	}
	
	public Post postDtoToPost(PostDto postDto)
	{
		Post post = modelmapper.map(postDto, Post.class);;
		/*
		 * post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */
		return post;
	}


	@Override
	public PostDto getPostById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "Id", id));
		
		//if(post==null) throw new ResourceNotFoundException("Post", "id", id);
		
		PostDto pdto = postToPostDto(post);
		
		return pdto;
	}


	@Override
	public PostDto updatePostById(PostDto postDto, long id) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->
				new ResourceNotFoundException("Category", "Id", id));
		
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		post.setCategory(category);
		
		postRepository.save(post);
		return postToPostDto(post);
	}


	@Override
	public void DeleteById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}


	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(category.getId());
		
		List<PostDto> postDtos = new ArrayList<>();
		
		for(int i=0;i<posts.size();i++)
		{
			postDtos.add(postToPostDto(posts.get(i)));
		}
		
		return postDtos;
	}

	
}
