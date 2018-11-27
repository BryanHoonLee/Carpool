package com.carpool.jambee.mongodb.repository;

import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends MongoRepository<UserData, String> { // <type you want to store, string id>

    // Find all users that have starting address from List of cities from proximity function
    List<UserData> findByStartingAddressIn(List<Address> addresses);

    // Find all users that have destination address from List of cities from proximity function
    List<UserData> findByDestinationAddressIn(List<Address> addresses);

    // Get a user's data through their ID
    UserData findByIdEquals(String id);

    UserData findByEmailEquals(String email);

    @Query(value = "{'startingAddress.city':?0}, {'startingAddress.state':?1}")
    List<UserData> findByStartingAddress(String city, String state);

    @Query(value = "{'endAddress.city':?0}, {'endAddress.state':?1}")
    List<UserData> findByDestinationAddress(String city, String state);

    // Find by starting city
    @Query(value = "{'startingAddress.city':?0}")
    List<UserData> findByStartingCity(String city);

    // Find by destination city
    @Query(value = "{'endAddress.city':?0}")
    List<UserData> findByDestinationCity(String city);

    @Query(value = "{'startingAddress.state':?0}")
    List<UserData> findByStartingState(String state);

    @Query(value = "{'endAddress.state':?0}")
    List<UserData> findByDestinationState(String state);

}
