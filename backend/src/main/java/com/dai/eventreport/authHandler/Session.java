package com.dai.eventreport.authHandler;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Document(collection = "sessions")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Session {

    @JsonAlias("_id")
    @Id private String id;

    @Field(name = "user_id")
    private String userId;

    private boolean admin;

    @Field(name = "expire_at")
    private Date expireAt;

    public Session() { }

    public Session(String userId, boolean admin) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.admin = admin;

        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusDays(1);

        this.expireAt = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public Date getExpireAt() {
        return expireAt;
    }
}
