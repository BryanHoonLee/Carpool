package com.carpool.jambee.mongodb.repository;

import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<UserData, String> { // <type you want to store, string id>

}
