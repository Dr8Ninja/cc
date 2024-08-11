package com.demo.compression.model;

public class huffmanLeafNode extends huffmanNode{
    private final char ch;

    public huffmanLeafNode(Character ch, int freq){
        super(freq);
        this.ch = ch;
    }

    public char getCh() {
        return ch;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
