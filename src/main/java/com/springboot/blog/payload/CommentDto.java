package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	
	private long id;
	
	@NotEmpty(message="Name Should not be Empty")
	private String name;
	
	@NotEmpty(message="Email Should not be Empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=10, message="Body should contain atlease 10 characters")
	private String body;

}
