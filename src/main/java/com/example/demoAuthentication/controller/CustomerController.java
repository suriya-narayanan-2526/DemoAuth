package com.example.demoAuthentication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoAuthentication.entity.Customers;
import com.example.demoAuthentication.sercurity.Jutil;
import com.example.demoAuthentication.service.CustomerService;

@RestController
@RequestMapping("/api/auth")
public class CustomerController {
	@Autowired
	private CustomerService service;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private Jutil jwt;
	@PostMapping("/register")
	public ResponseEntity<Customers> register(@RequestBody Customers customer)
	{
		 return ResponseEntity.ok().body(service.register(customer));
		
	}
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody Customers customer)
	{
		try {
	   Authentication auth =authManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getName(),customer.getPassword()));
	   UserDetails userDetails = (UserDetails)auth.getPrincipal();
	   String token =jwt.generateToken(userDetails);
	   return ResponseEntity.ok().body(Map.of("Token",token));
		}
		catch(Exception e)
		{
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid User name Password"));
		}
	
	}
	@GetMapping("/home")
	public ResponseEntity<String> home()
	{
		return ResponseEntity.ok().body("welcome namba to Home page");
	}

}
