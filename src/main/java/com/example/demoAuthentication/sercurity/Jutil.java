package com.example.demoAuthentication.sercurity;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class Jutil {
	private final String SECRET_KEY_STRING="jT8FSTZLDp9boskD5ISlso3N5h5FhfXE";
	private final SecretKey SECRET_KEY=Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
	
	public String generateToken(UserDetails user)
	{
		return  Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+ 1000*60*60))
				.signWith(SECRET_KEY,Jwts.SIG.HS256)
				.compact();
	}
	public boolean validateToken(String token,UserDetails user)
	{ 
		return extractUsername(token).equals(user.getUsername());
		
	}
	public String extractUsername(String token)
	{
		return Jwts.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

}
