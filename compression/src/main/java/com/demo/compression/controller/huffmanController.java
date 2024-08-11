package com.demo.compression.controller;

import com.demo.compression.model.huffmanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.compression.service.readFileService;
import com.demo.compression.service.huffmanService;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class huffmanController {

    @Autowired
    private readFileService readFileService;

    @Autowired
    private huffmanService huffmanService;

    @GetMapping("/build")
    public ResponseEntity<HashMap<Character, String>> getHuffmanTree(@RequestParam String filePath) throws IOException {
        HashMap<Character, Integer> freqMap = readFileService.calculateFrequency(filePath);
        huffmanNode root = huffmanService.buildHuffmanTree(freqMap);
        HashMap<Character, String> huffmanCode = huffmanService.generateHuffmanCode(root);

        return new ResponseEntity<>(huffmanCode, HttpStatus.OK);
    }

    @GetMapping("/encode")
    public ResponseEntity<String> encodeFile(@RequestParam String inputPath, @RequestParam String outputPath) throws IOException {
        HashMap<Character, Integer> freqMap = readFileService.calculateFrequency(inputPath);
        huffmanNode root = huffmanService.buildHuffmanTree(freqMap);
        HashMap<Character, String> huffmanCode = huffmanService.generateHuffmanCode(root);

        huffmanService.encodeText(inputPath, outputPath, huffmanCode);

        return new ResponseEntity<>("File successfully encoded and saved to " + outputPath,HttpStatus.OK);
    }

    @GetMapping("/decode")
    public ResponseEntity<String> decodeFile(@RequestParam String originalText, @RequestParam String encodedText, @RequestParam String outputPath) throws IOException {
        HashMap<Character, Integer> freqMap = readFileService.calculateFrequency(originalText);
        huffmanNode root = huffmanService.buildHuffmanTree(freqMap);
        HashMap<Character, String> huffmanCode = huffmanService.generateHuffmanCode(root);

        huffmanService.decodeText(encodedText, outputPath, huffmanCode);

        return new ResponseEntity<>("File successfully decoded and saved to: " + outputPath, HttpStatus.OK);
    }

}
