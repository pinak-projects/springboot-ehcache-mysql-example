package com.pinakjakhr.ehcache.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinakjakhr.ehcache.entity.Customer;
import com.pinakjakhr.ehcache.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/** API to create a customer */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> creatNewCustomer(@Valid @RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return ResponseEntity.ok("Customer created successfully.");
	}

	/** API to edit an existing customer */
	@PutMapping(value = "/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") long customerId, @Valid @RequestBody Customer customer) {
		customerService.fetchCustomerById(customerId);
		customer.setCustomerId(customerId);
		customerService.saveCustomer(customer);
		return ResponseEntity.ok("Customer updated successfully.");
	}

	/** API to fetch all customers */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Customer> Customer() {
		return customerService.fetchCustomersList();
	}

	/** API to fetch single customer details */
	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Customer getCustomerDetails(@PathVariable("customerId") long customerId) {
		return customerService.fetchCustomerById(customerId);
	}

	/** API to delete a customer */
	@DeleteMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") long customerId) {
		Customer customer = customerService.fetchCustomerById(customerId);
		customerService.deleteCustomer(customer);
		return ResponseEntity.ok("Customer deleted successfully.");
	}

}
