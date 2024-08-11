package com.demo.compression.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.compression.service.readFileService;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class frequencyController {

    @Autowired
    private readFileService readFileService;

    @GetMapping("/frequency")
    public ResponseEntity<HashMap<Character, Integer>> getFrequency(@RequestParam String filePath) throws IOException {
       HashMap<Character, Integer> freqMap = readFileService.calculateFrequency(filePath);
       return new  ResponseEntity<>(freqMap, HttpStatus.OK);
    }
}