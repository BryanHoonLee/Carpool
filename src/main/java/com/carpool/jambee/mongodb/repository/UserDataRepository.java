package com.carpool.jambee.mongodb;

import com.carpool.jambee.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<User, String> { // <type you want to store, string id>

}
