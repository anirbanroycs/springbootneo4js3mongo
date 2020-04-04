package com.techprimers.springbootneo4jexample1.repository;

import com.techprimers.springbootneo4jexample1.model.TestDate;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;

public interface TestDateRepository extends Neo4jRepository<TestDate, Long> {

    
}
