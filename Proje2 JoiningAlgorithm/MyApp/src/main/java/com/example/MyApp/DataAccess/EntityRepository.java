package com.example.MyApp.DataAccess;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.MyApp.entities.Entity;

public interface EntityRepository extends MongoRepository<Entity, String>{

}
