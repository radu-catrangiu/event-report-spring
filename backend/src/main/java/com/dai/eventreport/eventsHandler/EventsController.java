package com.dai.eventreport.eventsHandler;

import com.dai.eventreport.authHandler.Session;
import com.dai.eventreport.authHandler.SessionRepository;
import com.dai.eventreport.authHandler.User;
import com.dai.eventreport.authHandler.UserRepository;
import com.dai.eventreport.eventsHandler.responses.CreateResponse;
import com.dai.eventreport.imagesHandler.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository usersRepository;

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
    public ResponseEntity<CreateResponse> create(
        @RequestBody Event params,
        @RequestParam(name = "login_token", required = false) String loginToken
    ) {
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

        User[] admins = usersRepository.getAdmins();
        for (User admin : admins) {
            MailSenderThread thread = new MailSenderThread(javaMailSender, event, admin);
            thread.start();
        }

        imagesRepository.updateImageEventId(params.getImageId(), event.getId());

        if (loginToken != null) {
            Session session = sessionRepository.findSessionById(loginToken);

            if (session != null) {
                event.setClaimId(null);
                event.setOwnerId(session.getUserId());
            }
        }

        eventsRepository.save(event);

        CreateResponse createResponse = new CreateResponse(event, event.getClaimId());

        return new ResponseEntity<>(createResponse, HttpStatus.OK);
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

        Event event = eventsRepository.findEventById(eventId);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if (event.getOwnerId().equals(session.getUserId())) {
            session.setAdmin(true);
        }

        if (session == null || !session.isAdmin()) {
            return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        eventsRepository.deleteById(eventId);
        imagesRepository.deleteImageByEventId(eventId);

        return new ResponseEntity<>(eventId, HttpStatus.OK);
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claim(
        @RequestParam("login_token") String loginToken,
        @RequestBody EventClaim[] eventClaims
    ) {
        Session session = sessionRepository.findSessionById(loginToken);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (EventClaim eventClaim : eventClaims) {
            Event event = eventsRepository.findEventById(eventClaim.getEventId());
            if (event != null && event.getClaimId() != null && event.getClaimId().equals(eventClaim.getClaimId())) {
                event.setOwnerId(session.getUserId());
                event.setClaimId(null);
                eventsRepository.save(event);
            } 
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
