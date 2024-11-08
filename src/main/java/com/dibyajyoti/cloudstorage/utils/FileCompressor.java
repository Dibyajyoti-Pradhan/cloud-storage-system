package com.dibyajyoti.cloudstorage.utils;

import com.dibyajyoti.cloudstorage.models.FileNode;

public class FileCompressor {

    public static FileNode compress(FileNode fileNode) {
        int compressedSize = fileNode.getSize() / 2;
        String compressedName = fileNode.getName() + ".COMPRESSED";
        return new FileNode(compressedName, compressedSize, fileNode.getParent());
    }

    public static FileNode decompress(FileNode fileNode) {
        String name = fileNode.getName();
        if (!name.endsWith(".COMPRESSED")) {
            return null;
        }
        int originalSize = fileNode.getSize() * 2;
        String originalName = name.substring(0, name.length() - ".COMPRESSED".length());
        return new FileNode(originalName, originalSize, fileNode.getParent());
    }
}
