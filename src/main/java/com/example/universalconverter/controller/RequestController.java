package com.example.universalconverter.controller;

import com.example.universalconverter.converter.Converter;
import com.example.universalconverter.model.FoundException;
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

    @PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> convert(@RequestBody Request request) {
        Converter converter = new Converter();
        answer = converter.convertUnits(request);

        if (converter.getFoundException() == FoundException.BAD_REQUEST) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (converter.getFoundException() == FoundException.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
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
