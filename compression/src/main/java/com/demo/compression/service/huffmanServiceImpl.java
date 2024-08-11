package com.demo.compression.service;

import com.demo.compression.model.huffmanInternalNode;
import com.demo.compression.model.huffmanLeafNode;
import com.demo.compression.model.huffmanNode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Service
public class huffmanServiceImpl implements huffmanService {

    @Override
    public huffmanNode buildHuffmanTree(HashMap<Character, Integer> freqMap) {
        PriorityQueue<huffmanNode> pq = new PriorityQueue<>();

        for(Map.Entry<Character, Integer> entry : freqMap.entrySet()){
            pq.add(new huffmanLeafNode(entry.getKey(), entry.getValue()));
        }
        while(pq.size()>1){
            huffmanNode left = pq.poll();
            huffmanNode right = pq.poll();
            huffmanNode parent = new huffmanInternalNode(left, right);
            pq.add(parent);
        }
        return pq.poll();
    }

    @Override
    public HashMap<Character, String> generateHuffmanCode(huffmanNode root) {
        HashMap<Character, String> huffmanCode = new HashMap<>();
        generateHuffmanCodeRecursive(root, "", huffmanCode);
        return huffmanCode;
    }

    @Override
    public void generateHuffmanCodeRecursive(huffmanNode node, String code, HashMap<Character, String> huffmanCode) {
        if(node.isLeaf()){
            huffmanLeafNode leaf = (huffmanLeafNode) node;
            huffmanCode.put(leaf.getCh(), code);
        }
        else{
            huffmanInternalNode internalNode = (huffmanInternalNode) node;
            generateHuffmanCodeRecursive(internalNode.getLeft(), code+"0", huffmanCode);
            generateHuffmanCodeRecursive(internalNode.getRight(), code+"1", huffmanCode);
        }
    }

    @Override
    public void encodeText(String inputPath, String outputPath, HashMap<Character, String> huffmanCode) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
//        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);

        String line;
        StringBuilder encodedText = new StringBuilder();

        while((line=reader.readLine())!=null){
            for(char ch : line.toCharArray()){
                encodedText.append(huffmanCode.get(ch));
            }
            encodedText.append(System.lineSeparator());
        }
//        BitSet bitSet = new BitSet(encodedText.length());
//        for(int i=0; i<encodedText.length(); i++){
//            if(encodedText.charAt(i)=='1'){
//                bitSet.set(i);
//            }
//        }
//        byte[] byteArray = bitSet.toByteArray();
//        fileOutputStream.write(byteArray);
        writer.write(encodedText.toString());
    }

    public void decodeText(String encodedText, String decodedText, HashMap<Character, String> huffmanCode) throws IOException {
        HashMap<String, Character> reverseHuffmanCode = new HashMap<>();
        for(Map.Entry<Character, String> entry : huffmanCode.entrySet()){
            reverseHuffmanCode.put(entry.getValue(), entry.getKey());
        }

        BufferedReader reader = new BufferedReader(new FileReader(encodedText));
        FileWriter writer = new FileWriter(decodedText);

        StringBuilder decodeText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        String line;

        while ((line=reader.readLine())!=null) {
            for(char bit: line.toCharArray()){
                currentCode.append(bit);
                if(reverseHuffmanCode.containsKey(currentCode.toString())){
                    decodeText.append(reverseHuffmanCode.get(currentCode.toString()));
                    currentCode.setLength(0);
                }
            }
            decodeText.append(System.lineSeparator());
        }
        writer.write(decodeText.toString());
    }


}
