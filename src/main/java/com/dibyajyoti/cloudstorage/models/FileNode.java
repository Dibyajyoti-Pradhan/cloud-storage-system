package com.dibyajyoti.cloudstorage.models;

public class FileNode extends Node {

    private int size;

    public FileNode(String name, int size, DirectoryNode parent) {
        super(name, parent);
        this.size = size;
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
