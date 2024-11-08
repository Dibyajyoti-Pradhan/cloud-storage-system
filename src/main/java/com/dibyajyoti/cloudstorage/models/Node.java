package com.dibyajyoti.cloudstorage.models;

public abstract class Node {
    protected String name;
    protected DirectoryNode parent;

    public Node(String name, DirectoryNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public abstract boolean isFile();

    public abstract boolean isDirectory();

    public String getName() {
        return name;
    }

    public String getPath() {
        if (parent == null || parent.getPath().equals("/")) {
            return parent == null ? "/" : "/" + name;
        } else {
            return parent.getPath() + "/" + name;
        }
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}
