package com.dai.eventreport.eventsHandler;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EventClaim {
    private String claimId;
    private String eventId;

    public EventClaim() {}

    public EventClaim(String claimId, String eventId) {
        this.claimId = claimId;
        this.eventId = eventId;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}