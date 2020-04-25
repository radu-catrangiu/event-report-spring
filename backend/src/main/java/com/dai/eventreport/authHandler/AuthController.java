package com.dai.eventreport.authHandler;

import com.dai.eventreport.authHandler.responses.CreateResponse;
import com.dai.eventreport.authHandler.responses.LoginResponse;
import com.dai.eventreport.authHandler.responses.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> create(@RequestBody User params) {
        boolean goodEmail = params.getEmail().length() > 3;
        boolean goodPass = params.getPassword().length() > 3;

        if (!goodEmail || !goodPass) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = new User(params.getEmail(), params.getPassword());
        userRepository.insert(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        User user = userRepository.findUser(email, password);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Session session = new Session(user.getId(), user.isAdmin());
        sessionRepository.insert(session);

        LoginResponse loginResponse = new LoginResponse(email, session.getId(), true);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> token(@RequestParam(name = "login_token") String loginToken) {
        TokenResponse tokenResponse = new TokenResponse("email", true);

        Session session = sessionRepository.findSessionById(loginToken);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userRepository.findUserById(session.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
