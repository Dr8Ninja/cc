package com.demo.compression.service;

import com.demo.compression.model.huffmanNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface huffmanService {
    public huffmanNode buildHuffmanTree(HashMap<Character, Integer> freqMap);

    public HashMap<Character, String> generateHuffmanCode(huffmanNode root);

    public void generateHuffmanCodeRecursive(huffmanNode node, String code, HashMap<Character, String> huffmanCode);

    public void encodeText(String inputPath, String outputPath, HashMap<Character, String> huffmanCode) throws IOException;

    public void decodeText(String encodedText, String decodedText, HashMap<Character, String> huffmanCode) throws IOException;
}
