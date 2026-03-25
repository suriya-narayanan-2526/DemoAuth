package com.example.demoAuthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoAuthentication.entity.Customers;
import com.example.demoAuthentication.repo.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository cusRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	public Customers register(Customers customer)
	{
		customer.setPassword(encoder.encode(customer.getPassword()));
		return cusRepo.save(customer);
	}

}
