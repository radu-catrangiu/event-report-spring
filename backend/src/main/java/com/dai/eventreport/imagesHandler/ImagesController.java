package com.dai.eventreport.imagesHandler;

import com.dai.eventreport.imagesHandler.responses.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/image")
public class ImagesController {

    @Autowired
    ImagesRepository imagesRepository;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestParam("image") MultipartFile image) {
        String encoded = null;

        try {
            byte[] bytes = Base64.getEncoder().encode(image.getBytes());
            encoded = new String(bytes);
        } catch (IOException e) {
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Image newImage = new Image(encoded);
        imagesRepository.insert(newImage);

        UploadResponse uploadResponse = new UploadResponse(newImage.getId());

        return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> get(@PathVariable("imageId") String imageId) {
        Image image = imagesRepository.findImageById(imageId);

        byte[] bytes = Base64.getDecoder().decode(image.getEncoded());

        return new ResponseEntity<>(bytes, HttpStatus.NOT_IMPLEMENTED);
    }
}
