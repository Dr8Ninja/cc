package com.demo.compression.model;

public class huffmanInternalNode extends huffmanNode{
    private final huffmanNode left;
    private final huffmanNode right;

    public huffmanInternalNode(huffmanNode left, huffmanNode right){
        super(left.getFreq() + right.getFreq());
        this.left=left;
        this.right=right;
    }

    public huffmanNode getLeft() {
        return left;
    }

    public huffmanNode getRight() {
        return right;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
