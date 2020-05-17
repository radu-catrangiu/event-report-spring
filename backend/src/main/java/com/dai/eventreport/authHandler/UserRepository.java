package com.dai.eventreport.authHandler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{ email: ?0, password: ?1 }", fields = "{ password: 0}")
    User findUser(String email, String password);

    @Query(value = "{ _id: ?0 }")
    User findUserById(String id);

    @Query(value = "{ admin: true }")
    User[] getAdmins();
}
