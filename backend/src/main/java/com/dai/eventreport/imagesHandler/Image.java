package com.dai.eventreport.imagesHandler;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Document(collection = "images")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Image {

    @JsonAlias("_id")
    @Id private String id;

    @Field(name = "expire_at")
    private Date expireAt;

    @Field(name = "event_id")
    private String eventId;
    private String encoded;

    public Image() { }

    public Image(String encoded) {
        this.id = UUID.randomUUID().toString();
        this.encoded = encoded;

        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusMinutes(5);

        this.expireAt = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}
