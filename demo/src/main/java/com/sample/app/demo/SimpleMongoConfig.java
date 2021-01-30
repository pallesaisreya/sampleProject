package com.sample.app.demo;

import java.net.UnknownHostException;

import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class SimpleMongoConfig {

	@Value("${mongo.dbname}")
	private String databaseName;

	@Value("${mongo.username}")
	private String username;
	
	@Value("${mongo.password}")
	private String password;

	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.port}")
	private int port;

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		MongoClientURI uri = new MongoClientURI("mongodb://"+username+":"+password+"@"+host+":"+port+"/"+databaseName);
		MongoClient mongoClient = new MongoClient();
		SimpleMongoDbFactory mongoDbFactory=new SimpleMongoDbFactory(uri);
		return mongoDbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
	
	
	
}
