package com.pinakjakhr.ehcache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinakjakhr.ehcache.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
