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

	/*
	 *  @CacheConfig - tells Spring where to store cache for this class
	 * 	@CacheEvict - will clear the cache when we delete or update any customer info from the database.
	 *  @Cacheable - Each time the method is invoked, it is checked whether the method has been already invoked for 
	 *   		     the given arguments. If no value is found in the cache, the target method will be invoked and 
	 *               the returned value will be stored in the associated cache.
	 *  @CachePut -  always lets the method execute. It is used to update the cache with the result of the method execution
	 */
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Caching(evict = @CacheEvict(allEntries = true), put = @CachePut)
	public Customer saveCustomer(Customer customer) {
		log.info("saveCustomer called for creating a customer...");
		return customerRepository.save(customer);
	}

	@Cacheable
	@Override
	public Customer fetchCustomerById(long customerId) {
		log.info("fetchCustomerById called for fetching a customer...");
		return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found."));
	}

	@Cacheable
	@Override
	public List<Customer> fetchCustomersList() {
		log.info("fetchCustomersList called for fetching all customers...");
		simulateSlowService();
		return Optional.ofNullable(customerRepository.findAll()).orElse(new ArrayList<>());
	}

	private void simulateSlowService() {
		try {
			log.info("simulateSlowService called...");
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Caching(evict = @CacheEvict(allEntries = true))
	@Override
	public void deleteCustomer(Customer customer) {
		log.info("deleteCustomer called for deleting a customer...");
		customerRepository.delete(customer);
	}

}
