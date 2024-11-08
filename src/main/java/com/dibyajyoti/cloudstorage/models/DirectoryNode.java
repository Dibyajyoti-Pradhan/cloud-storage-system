package com.dibyajyoti.cloudstorage.models;

import java.util.HashMap;
import java.util.Map;

public class DirectoryNode extends Node {

    private Map<String, Node> children;

    public DirectoryNode(String name, DirectoryNode parent) {
        super(name, parent);
        this.children = new HashMap<>();
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addChild(Node node) {
        children.put(node.getName(), node);
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public void removeChild(String name) {
        children.remove(name);
    }

    public Map<String, Node> getChildren() {
        return children;
    }
}
