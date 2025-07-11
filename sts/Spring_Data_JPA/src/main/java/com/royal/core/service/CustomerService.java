package com.royal.core.service;

import java.util.List;
import java.util.Optional;

import com.royal.core.entity.Customer;

public interface CustomerService {
	Customer saveCustomer(Customer customer);
	List<Customer> getAllCustomers();
	Optional<Customer> getCustomerById(Long id);
	void deleteCustomer(Long id);
}
