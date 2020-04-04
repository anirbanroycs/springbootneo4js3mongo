package com.techprimers.springbootneo4jexample1.repository;

import com.techprimers.springbootneo4jexample1.Document.Navigations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NavigationsRepository extends MongoRepository<Navigations, Long> {
	
	
	
}
