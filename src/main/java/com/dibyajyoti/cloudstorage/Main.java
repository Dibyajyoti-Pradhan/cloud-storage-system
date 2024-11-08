package com.dibyajyoti.cloudstorage;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        CloudStorage storage = new CloudStorageImpl();

        // Example usage:

        // Level 1 functionality
        storage.addFile("/path/to/file.txt", 1234);
        storage.copyFile("/path/to/file.txt", "/path/to/copy.txt");
        Optional<Integer> size = storage.getFileSize("/path/to/copy.txt");
        System.out.println("File size: " + size.orElse(-1));

        // Level 3 functionality
        storage.addUser("user1", 5000);
        Optional<Integer> remainingCapacity = storage.addFileBy("user1", "/user1/file.txt", 1000);
        System.out.println("User1 remaining capacity: " + remainingCapacity.orElse(-1));

        // Level 4 functionality
        remainingCapacity = storage.compressFile("user1", "/user1/file.txt");
        System.out.println("After compression, User1 remaining capacity: " + remainingCapacity.orElse(-1));

        size = storage.getFileSize("/user1/file.txt.COMPRESSED");
        System.out.println("Compressed file size: " + size.orElse(-1));
    }
}
