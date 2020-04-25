package com.dai.eventreport.eventsHandler;

import com.dai.eventreport.authHandler.Session;
import com.dai.eventreport.authHandler.SessionRepository;
import com.dai.eventreport.imagesHandler.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @GetMapping
    public ResponseEntity<List<Event>> list() {
        Sort sort = Sort.by("report_date").descending();
        List<Event> eventsList = eventsRepository.findAll(sort);

        return new ResponseEntity<>(eventsList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event params) {
        Event event = new Event(
                params.getTitle(),
                params.getDescription(),
                params.getTag(),
                params.getLocation(),
                params.getImageId()
        );

        boolean imageExists = imagesRepository.existsById(params.getImageId());
        if (!imageExists) {
            return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // TODO: Send mail to all admins

        imagesRepository.updateImageEventId(params.getImageId(), event.getId());

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<String> update(@PathVariable("eventId") String eventId, @RequestBody EventUpdate eventUpdate) {
        Session session = sessionRepository.findSessionById(eventUpdate.getLoginToken());

        if (session == null || !session.isAdmin()) {
            return  new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Event event = eventsRepository.findEventById(eventId);
        event.setResolved(eventUpdate.isResolved());

        eventsRepository.save(event);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> delete(
            @PathVariable("eventId") String eventId,
            @RequestParam("login_token") String loginToken
    ) {
        Session session = sessionRepository.findSessionById(loginToken);

        if (session == null || !session.isAdmin()) {
            return  new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        eventsRepository.deleteById(eventId);
        imagesRepository.deleteImageByEventId(eventId);

        return new ResponseEntity<>(eventId, HttpStatus.OK);
    }
}
