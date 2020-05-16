package com.dai.eventreport.eventsHandler.responses;

import com.dai.eventreport.eventsHandler.Event;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateResponse {
    Event event;
    String claimId;

    public CreateResponse() {}

    public CreateResponse(Event event, String claimId) {
        this.event = event;
        this.claimId = claimId;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getClaimId() {
        return this.claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }
    
}