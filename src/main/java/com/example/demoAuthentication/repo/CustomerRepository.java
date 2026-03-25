package com.example.demoAuthentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoAuthentication.entity.Customers;

public interface CustomerRepository extends JpaRepository<Customers,Long>{
	Optional<Customers> findByName(String name);

}
