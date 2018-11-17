package com.carpool.jambee.mongodb.repository;

import com.carpool.jambee.CityData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityDataRepository extends MongoRepository<CityData, String> {

}
