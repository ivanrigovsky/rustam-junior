package com.example.springbootbook.repository;

import com.example.springbootbook.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends MongoRepository<Coffee, String> {
}
