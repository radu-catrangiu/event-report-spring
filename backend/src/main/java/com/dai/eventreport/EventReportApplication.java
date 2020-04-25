package com.dai.eventreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class EventReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventReportApplication.class, args);
	}
}
