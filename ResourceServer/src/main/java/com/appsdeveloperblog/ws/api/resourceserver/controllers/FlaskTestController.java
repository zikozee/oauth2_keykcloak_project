package com.appsdeveloperblog.ws.api.resourceserver.controllers;

import com.appsdeveloperblog.ws.api.resourceserver.request.Student;
import com.appsdeveloperblog.ws.api.resourceserver.response.GradesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author : zikoz
 * @created : 14 Jul, 2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class FlaskTestController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<String> student(@RequestBody Student student) {
        GradesResult grades = restTemplate.getForObject("http://data-aggregation-service/calculateGrades", GradesResult.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Sent the Student to the Data Aggregation Service: %s \nAnd got back:\n %s", student.toString(), grades.toString()));
    }
}
