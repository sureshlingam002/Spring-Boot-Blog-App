package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto)
	{
		String token = authService.login(loginDto);
		
		JWTAuthResponse authResponse = new JWTAuthResponse();
		authResponse.setAccessToken(token);
		
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}
	
	@PostMapping(value= {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
	{
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
