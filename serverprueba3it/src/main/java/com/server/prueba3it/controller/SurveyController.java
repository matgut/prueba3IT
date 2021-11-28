package com.server.prueba3it.controller;


import com.server.prueba3it.model.Response;
import com.server.prueba3it.model.Survey;
import com.server.prueba3it.service.implementation.SurveyImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyImplementation surveyImplementation;

    @GetMapping("/list")
    public ResponseEntity<Response> getSurveys(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Surveys", surveyImplementation.list(30)))
                        .message("Encuestas Encontradas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createSurvey(@RequestBody @Valid Survey survey){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Survey", surveyImplementation.create(survey)))
                        .message("Encuesta creada")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

}
