package com.dai.eventreport.imagesHandler.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UploadResponse {
    String imageId;

    public UploadResponse(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }
}
