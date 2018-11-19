package com.carpool.jambee.mongodb.repository;

import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDataRepository extends MongoRepository<CityData, String> {

    // find all cities with same state id
    @Query(value = "{'stateID':?0}")
    List<CityData> findByStateID(String stateID);
}
