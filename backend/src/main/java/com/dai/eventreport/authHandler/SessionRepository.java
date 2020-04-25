package com.dai.eventreport.authHandler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SessionRepository extends MongoRepository<Session, String> {
    @Query(value = "{ _id: ?0 }")
    Session findSessionById(String id);
}
