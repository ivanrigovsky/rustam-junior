package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, Long> {

    List<Message> findByTag(String tag);
    List<Message> findByTagLike(String tag);
}
