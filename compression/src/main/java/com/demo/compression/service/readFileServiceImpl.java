package com.demo.compression.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Service
public class readFileServiceImpl implements readFileService{

    public HashMap<Character, Integer> calculateFrequency(String filePath) throws IOException{
        HashMap<Character, Integer> freqMap = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                for(char ch:line.toCharArray()){
                    freqMap.merge(ch, 1, Integer::sum);
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to read file: " + filePath, e);
        }
        return freqMap;
    }
}
