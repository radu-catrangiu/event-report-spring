package com.dai.eventreport.eventsHandler;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

@Document(collection = "events")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Event {

    static class Location {
        private double lat, lng;

        public Location() { }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }

    @JsonAlias(value = "_id")
    @Id private String id;
    private String title;
    private String description;
    private String tag;
    private Location location;
    @Field(name = "image_id")
    private String imageId;
    private boolean resolved;
    @Field(name = "report_date")
    private Date reportDate;
    @Field(name = "owner_id")
    private String ownerId;
    @JsonIgnore
    private String claimId;

    public Event(String title, String description, String tag, Location location, String imageId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.tag = tag.toLowerCase();
        this.location = location;
        this.imageId = imageId.toLowerCase();
        this.resolved = false;
        this.reportDate = new Date();
        this.ownerId = null;
        this.claimId = UUID.randomUUID().toString();
    }

    public Event() { }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @JsonSerialize @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public Location getLocation() {
        return location;
    }

    public String getImageId() {
        return imageId;
    }

    public boolean isResolved() {
        return resolved;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }
}
