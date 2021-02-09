package com.example.universalconverter.controller;

import com.example.universalconverter.converter.Converter;
import com.example.universalconverter.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RequestController {

    @Autowired
    private org.springframework.boot.ApplicationArguments applicationArguments;

    private String answer;
    private Request userRequest;

    @PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> convert(@RequestBody Request request) {
//        userRequest = new Request(request.getFromUnits(), request.getToUnits());
//        Converter converter = new Converter();
//        answer = converter.convertUnits(userRequest);
        answer = request.toString();
        return new ResponseEntity<Request>(new Request(request.getFromUnits(), request.getToUnits()), HttpStatus.OK);
    }

    @GetMapping
    public String mainPage() {
        return "Сервис предоставляет единсвтенный метод POST /convert для передачи JSON в теле запроса";
    }

    @GetMapping("/convert")
    public String answer() {
        return answer;
    }


}
