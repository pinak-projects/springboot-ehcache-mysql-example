package com.pinakjakhr.ehcache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinakjakhr.ehcache.entity.Customer;
import com.pinakjakhr.ehcache.repository.CustomerRepository;
import com.pinakjakhr.ehcache.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer fetchCustomerById(long customerId) {
		return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found."));
	}

	@Override
	public List<Customer> fetchCustomersList() {
		return Optional.ofNullable(customerRepository.findAll()).orElse(new ArrayList<>());
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

}
