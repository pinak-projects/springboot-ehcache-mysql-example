package com.pinakjakhr.ehcache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.pinakjakhr.ehcache.entity.Customer;
import com.pinakjakhr.ehcache.repository.CustomerRepository;
import com.pinakjakhr.ehcache.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "customer")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Caching(evict = @CacheEvict(allEntries = true), put = @CachePut)
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Cacheable
	@Override
	public Customer fetchCustomerById(long customerId) {
		return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found."));
	}

	@Cacheable
	@Override
	public List<Customer> fetchCustomersList() {
		simulateSlowService();
		return Optional.ofNullable(customerRepository.findAll()).orElse(new ArrayList<>());
	}

	private void simulateSlowService() {
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Caching(evict = @CacheEvict(allEntries = true))
	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

}
