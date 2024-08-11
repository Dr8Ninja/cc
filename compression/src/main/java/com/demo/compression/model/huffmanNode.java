package com.demo.compression.model;

public abstract class huffmanNode implements Comparable<huffmanNode> {
    private final int freq;

    public huffmanNode(int freq){
        this.freq = freq;
    }

    public int getFreq(){
        return freq;
    }

    @Override
    public int compareTo(huffmanNode other){
        return Integer.compare(this.freq, other.freq);
    }

    public abstract boolean isLeaf();
}
