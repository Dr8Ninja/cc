package com.demo.compression.service;

import java.io.IOException;
import java.util.HashMap;

public interface readFileService {
    public HashMap<Character, Integer> calculateFrequency(String filePath) throws IOException;
}
