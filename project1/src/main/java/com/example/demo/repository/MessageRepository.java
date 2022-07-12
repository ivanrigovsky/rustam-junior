package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.mongodb.repository.MongoRepository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends MongoRepository<Message, Long> {

    Page<Message> findAll(Pageable pageable);

    Page<Message> findByTagLike(String tag, Pageable pageable);

    @Query("from Message m where m.author = :author ")
    Page<Message> findByUser(Pageable pageable, @Param("author") String username);
}
