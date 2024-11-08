package com.dibyajyoti.cloudstorage;

import java.util.Optional;

public interface CloudStorage {

    boolean addFile(String name, int size);

    boolean copyFile(String source, String destination);

    Optional<Integer> getFileSize(String name);

    boolean addUser(String userId, int capacity);

    Optional<Integer> addFileBy(String userId, String name, int size);

    Optional<Integer> updateCapacity(String userId, int capacity);

    Optional<Integer> compressFile(String userId, String name);

    Optional<Integer> decompressFile(String userId, String name);
}
