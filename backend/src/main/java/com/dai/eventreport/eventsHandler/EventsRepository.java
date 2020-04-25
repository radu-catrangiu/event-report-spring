package com.dai.eventreport.eventsHandler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EventsRepository extends MongoRepository<Event, String> {
    @Query(value = "{ _id: ?0 }")
    Event findEventById(String id);
}
