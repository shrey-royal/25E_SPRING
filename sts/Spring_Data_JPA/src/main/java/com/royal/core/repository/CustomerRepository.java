package com.royal.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.royal.core.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	//
}
