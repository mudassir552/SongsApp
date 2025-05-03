package com.demo.MongoConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;


//import com.mongodb.client.MongoClients;

@Configuration
 public class MongoConfig {
  
  private String databaseName = "SongsDB";
  //private String connectionString ="mongodb://localhost:27017";
  @Value("${connection.url}")
  private String CONNECTION_URL;


  @Bean 
  public MongoTemplate mongoTemplate() {
	  return new MongoTemplate(MongoClients.create(CONNECTION_URL), databaseName); } }
 
