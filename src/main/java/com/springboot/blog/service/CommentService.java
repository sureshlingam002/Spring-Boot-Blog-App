package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {

	public CommentDto createComment(CommentDto commentDto, long commentId);

	public List<CommentDto> getAllComments(long postId);
	
	public CommentDto getCommentById(long postId, long CommentId);
	
	public CommentDto updateCommentById(CommentDto commentDto, long postId, long commentId);
	
	public String deleteCommentById(long commentId, long postId);

}
