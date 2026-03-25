package com.example.demoAuthentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demoAuthentication.entity.Customers;
import com.example.demoAuthentication.repo.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private CustomerRepository cusRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customers customer = cusRepo.findByName(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
		return new User(customer.getName(),customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

}
