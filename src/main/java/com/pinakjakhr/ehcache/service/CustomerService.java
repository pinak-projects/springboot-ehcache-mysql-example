package com.pinakjakhr.ehcache.service;

import java.util.List;

import com.pinakjakhr.ehcache.entity.Customer;

public interface CustomerService {

	Customer saveCustomer(Customer customer);

	Customer fetchCustomerById(long customerId);

	List<Customer> fetchCustomersList();

	void deleteCustomer(Customer customer);

}
