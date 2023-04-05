/**
 * 
 */
package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	
	
	private long id;
	
	@NotEmpty
	@Size(min=2, message="Post title should have atleast 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min=5, message="Post description should have atleast 5 characters")
	private String description;
	
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	
	private long categoryId;
	

}
