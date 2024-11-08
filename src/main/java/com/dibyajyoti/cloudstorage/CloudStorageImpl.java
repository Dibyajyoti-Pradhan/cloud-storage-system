package com.dibyajyoti.cloudstorage;

import com.dibyajyoti.cloudstorage.models.DirectoryNode;
import com.dibyajyoti.cloudstorage.models.FileNode;
import com.dibyajyoti.cloudstorage.models.Node;
import com.dibyajyoti.cloudstorage.models.User;
import com.dibyajyoti.cloudstorage.utils.FileCompressor;

import java.util.*;

public class CloudStorageImpl implements CloudStorage {

    private DirectoryNode root;
    private Map<String, User> users;
    private Map<String, String> fileOwnership;

    public CloudStorageImpl() {
        this.root = new DirectoryNode("/", null);
        this.users = new HashMap<>();
        this.fileOwnership = new HashMap<>();
    }

    @Override
    public boolean addFile(String name, int size) {
        if (!isValidName(name) || size <= 0) {
            return false;
        }
        if (getNode(name) != null) {
            return false;
        }
        FileNode file = new FileNode(getFileName(name), size, null);
        addNodeToSystem(name, file);
        return true;
    }

    @Override
    public boolean copyFile(String source, String destination) {
        if (!isValidName(source) || !isValidName(destination)) {
            return false;
        }
        Node sourceNode = getNode(source);
        if (sourceNode == null || !sourceNode.isFile()) {
            return false;
        }
        if (getNode(destination) != null) {
            return false;
        }
        FileNode sourceFile = (FileNode) sourceNode;
        FileNode newFile = new FileNode(getFileName(destination), sourceFile.getSize(), null);
        addNodeToSystem(destination, newFile);
        return true;
    }

    @Override
    public Optional<Integer> getFileSize(String name) {
        if (!isValidName(name)) {
            return Optional.empty();
        }
        Node node = getNode(name);
        if (node == null || !node.isFile()) {
            return Optional.empty();
        }
        return Optional.of(((FileNode) node).getSize());
    }

    @Override
    public boolean addUser(String userId, int capacity) {
        if (userId == null || userId.isEmpty() || capacity <= 0) {
            return false;
        }
        if (users.containsKey(userId)) {
            return false;
        }
        users.put(userId, new User(userId, capacity));
        return true;
    }

    @Override
    public Optional<Integer> addFileBy(String userId, String name, int size) {
        if (!users.containsKey(userId) || !isValidName(name) || size <= 0) {
            return Optional.empty();
        }
        if (getNode(name) != null) {
            return Optional.empty();
        }
        User user = users.get(userId);
        if (user.getRemainingCapacity() < size) {
            return Optional.empty();
        }
        FileNode file = new FileNode(getFileName(name), size, null);
        addNodeToSystem(name, file);
        fileOwnership.put(name, userId);
        user.addUsedCapacity(size);
        return Optional.of(user.getRemainingCapacity());
    }

    @Override
    public Optional<Integer> updateCapacity(String userId, int capacity) {
        if (!users.containsKey(userId) || capacity <= 0) {
            return Optional.empty();
        }
        User user = users.get(userId);
        user.setTotalCapacity(capacity);
        while (user.getUsedCapacity() > capacity) {
            FileNode fileToRemove = getLargestFileOwnedBy(userId);
            if (fileToRemove == null) {
                break;
            }
            int fileSize = fileToRemove.getSize();
            deleteFile(fileToRemove.getPath());
            fileOwnership.remove(fileToRemove.getPath());
            user.subtractUsedCapacity(fileSize);
        }
        return Optional.of(user.getRemainingCapacity());
    }

    @Override
    public Optional<Integer> compressFile(String userId, String name) {
        if (!users.containsKey(userId) || !isValidName(name)) {
            return Optional.empty();
        }
        Node node = getNode(name);
        if (node == null || !node.isFile()) {
            return Optional.empty();
        }
        String ownerId = fileOwnership.get(name);
        if (!userId.equals(ownerId)) {
            return Optional.empty();
        }
        User user = users.get(userId);
        FileNode fileNode = (FileNode) node;
        FileNode compressedFile = FileCompressor.compress(fileNode);
        int sizeReduction = fileNode.getSize() - compressedFile.getSize();

        user.subtractUsedCapacity(sizeReduction);

        deleteFile(name);
        fileOwnership.remove(name);

        String compressedName = name + ".COMPRESSED";
        addNodeToSystem(compressedName, compressedFile);
        fileOwnership.put(compressedName, userId);

        return Optional.of(user.getRemainingCapacity());
    }

    @Override
    public Optional<Integer> decompressFile(String userId, String name) {
        if (!users.containsKey(userId) || !isValidName(name) || !name.endsWith(".COMPRESSED")) {
            return Optional.empty();
        }
        Node node = getNode(name);
        if (node == null || !node.isFile()) {
            return Optional.empty();
        }
        String ownerId = fileOwnership.get(name);
        if (!userId.equals(ownerId)) {
            return Optional.empty();
        }
        User user = users.get(userId);
        FileNode fileNode = (FileNode) node;
        FileNode decompressedFile = FileCompressor.decompress(fileNode);

        int sizeIncrease = decompressedFile.getSize() - fileNode.getSize();
        if (user.getRemainingCapacity() < sizeIncrease) {
            return Optional.empty();
        }
        user.addUsedCapacity(sizeIncrease);

        deleteFile(name);
        fileOwnership.remove(name);

        String decompressedName = name.substring(0, name.length() - ".COMPRESSED".length());
        addNodeToSystem(decompressedName, decompressedFile);
        fileOwnership.put(decompressedName, userId);

        return Optional.of(user.getRemainingCapacity());
    }

    // Helper methods

    private boolean isValidName(String name) {
        if (name == null || name.isEmpty() || name.equals("/")) {
            return false;
        }
        // Updated regex to include uppercase letters
        return name.matches("(/[a-zA-Z0-9_\\.]+)+");
    }

    private String getFileName(String path) {
        String[] parts = path.split("/");
        return parts[parts.length - 1];
    }

    private void addNodeToSystem(String path, Node node) {
        String[] parts = path.split("/");
        DirectoryNode current = root;
        for (int i = 1; i < parts.length - 1; i++) {
            Node child = current.getChild(parts[i]);
            if (child == null || !child.isDirectory()) {
                DirectoryNode newDir = new DirectoryNode(parts[i], current);
                current.addChild(newDir);
                current = newDir;
            } else {
                current = (DirectoryNode) child;
            }
        }
        current.addChild(node);
        node.setParent(current);
    }

    private Node getNode(String path) {
        String[] parts = path.split("/");
        Node current = root;
        for (int i = 1; i < parts.length; i++) {
            if (!current.isDirectory()) {
                return null;
            }
            current = ((DirectoryNode) current).getChild(parts[i]);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    private void deleteFile(String path) {
        String[] parts = path.split("/");
        DirectoryNode current = root;
        for (int i = 1; i < parts.length - 1; i++) {
            Node child = current.getChild(parts[i]);
            if (child == null || !child.isDirectory()) {
                return;
            }
            current = (DirectoryNode) child;
        }
        current.removeChild(parts[parts.length - 1]);
    }

    private FileNode getLargestFileOwnedBy(String userId) {
        List<FileNode> files = new ArrayList<>();
        collectFilesOwnedBy(root, userId, files);
        if (files.isEmpty()) {
            return null;
        }
        files.sort(Comparator.comparingInt(FileNode::getSize).reversed());
        return files.get(0);
    }

    private void collectFilesOwnedBy(Node node, String userId, List<FileNode> files) {
        if (node.isFile()) {
            String ownerId = fileOwnership.get(node.getPath());
            if (userId.equals(ownerId)) {
                files.add((FileNode) node);
            }
        } else {
            DirectoryNode dir = (DirectoryNode) node;
            for (Node child : dir.getChildren().values()) {
                collectFilesOwnedBy(child, userId, files);
            }
        }
    }
}
