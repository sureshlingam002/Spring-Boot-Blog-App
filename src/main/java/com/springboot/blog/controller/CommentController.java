package com.springboot.blog.controller;

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

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping("/{post_id}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable (name="post_id") long id )
	{
		return new ResponseEntity<>(commentService.createComment(commentDto,id), HttpStatus.CREATED);
	}
	
	@GetMapping("/{post_id}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsOfAPost(@PathVariable (name="post_id") long id)
	{
		return new ResponseEntity<>(commentService.getAllComments(id), HttpStatus.OK);
	}
	
	@GetMapping("/{post_id}/comments/{comment_id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable (name="post_id") long postId, @PathVariable (name="comment_id") long commentId)
	{
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	@PutMapping("/{post_id}/comments/{comment_id}")
	public ResponseEntity<CommentDto> updateCommentById(@Valid @RequestBody CommentDto commentDto, @PathVariable (name="post_id") long postId, @PathVariable (name="comment_id") long commentId)
	{
		return new ResponseEntity<>(commentService.updateCommentById(commentDto, postId, commentId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{post_id}/comments/{comment_id}")
	public String deleteCommentById(@PathVariable (name="comment_id") long commentId, @PathVariable (name="post_id") long postId)
	{
		return commentService.deleteCommentById(commentId,postId);
	}
}
