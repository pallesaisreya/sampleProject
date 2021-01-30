package com.sample.app.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sample.app.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
