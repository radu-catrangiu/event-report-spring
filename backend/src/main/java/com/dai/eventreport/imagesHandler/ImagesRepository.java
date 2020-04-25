package com.dai.eventreport.imagesHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ImagesRepository {

    private final String COLLECTION = "images";
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ImagesRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    void insert(Image image) {
        mongoTemplate.insert(image);
    }

    Image findImageById(String id) {
        return mongoTemplate.findById(id, Image.class);
    }

    public boolean existsById(String imageId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(imageId));

        return mongoTemplate.exists(query, COLLECTION);
    }

    public void updateImageEventId(String imageId, String eventId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(imageId));

        Update update = new Update();
        update.unset("expire_at");
        update.set("event_id", eventId);

        mongoTemplate.updateFirst(query, update, COLLECTION);
    }

    public void deleteImageByEventId(String eventId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("event_id").is(eventId));

        mongoTemplate.remove(query, COLLECTION);
    }
}
