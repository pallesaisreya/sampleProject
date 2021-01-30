package com.sample.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.demo.model.User;
import com.sample.app.demo.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/user/save")
	public ResponseEntity<?> saveUser(@RequestBody User user){
		
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
		
	}
	

}
