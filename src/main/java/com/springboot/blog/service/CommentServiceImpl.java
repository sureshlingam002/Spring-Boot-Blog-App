package com.springboot.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;


	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, long id) {	
		
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		
		Comment comment= commentDtoToComment(commentDto);
		
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		
		return commentToCommentDto(newComment);
	}

	@Override
	public List<CommentDto> getAllComments(long id) {
		
		List<Comment> allComments = commentRepository.findByPostId(id);
		return allComments.stream().map(comment -> commentToCommentDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		
		if(comment.getPost().getId() != post.getId())
		{
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This comment does not belong to this post");
		}
		
		return commentToCommentDto(comment);
	}

	@Override
	public CommentDto updateCommentById(CommentDto commentDto, long postId, long commentId) {
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		
		if(comment.getPost().getId() != post.getId())
		{
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This comment does not belong to this post");
		}
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		commentRepository.save(comment);
		
		return commentToCommentDto(comment);		
	}

	@Override
	public String deleteCommentById(long commentId, long postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(comment.getPost().getId() != post.getId())
		{
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This comment does not belong to this post");
		}
		
		commentRepository.delete(comment);
		return "Comment Deleted Successfully";
	}
	
	
	private CommentDto commentToCommentDto(Comment comment) {
		
		CommentDto cdto = mapper.map(comment, CommentDto.class);;
//		cdto.setId(comment.getId());
//		cdto.setName(comment.getName());
//		cdto.setEmail(comment.getEmail());
//		cdto.setBody(comment.getBody());
//		
		return cdto;
	}
	
	private Comment commentDtoToComment(CommentDto commentDto)
	{
		Comment comment =mapper.map(commentDto, Comment.class);;
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}
	

}
