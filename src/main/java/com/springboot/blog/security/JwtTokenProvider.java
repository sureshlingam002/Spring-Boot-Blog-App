package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long expirationDate;
	
	//generate Jwt token
	public String generateToken(Authentication authentication)
	{
		String userName =  authentication.getName();
		
		Date currentDate = new Date();
		
		Date expiryDate = new Date(currentDate.getTime() + expirationDate);
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(key())
				.compact();
		
		return token;
	
	}
	
	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUserName(String token)
	{
		Claims claim = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		
		String username = claim.getSubject();
		return username;
	}
	
	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build().parse(token);
			return true;
		}
		catch(MalformedJwtException ex){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		}
		catch(ExpiredJwtException ex){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		}
		catch(UnsupportedJwtException ex){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		}
		catch(IllegalArgumentException ex){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
	}

}
