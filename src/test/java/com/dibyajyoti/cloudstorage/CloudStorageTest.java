package com.dibyajyoti.cloudstorage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class CloudStorageTest {

    @Test
    void testAddFile() {
        CloudStorage storage = new CloudStorageImpl();
        assertTrue(storage.addFile("/file1.txt", 100));
        assertFalse(storage.addFile("/file1.txt", 200)); // File already exists
    }

    @Test
    void testCopyFile() {
        CloudStorage storage = new CloudStorageImpl();
        storage.addFile("/file1.txt", 100);
        assertTrue(storage.copyFile("/file1.txt", "/file2.txt"));
        assertFalse(storage.copyFile("/file1.txt", "/file2.txt")); // Destination exists
    }

    @Test
    void testGetFileSize() {
        CloudStorage storage = new CloudStorageImpl();
        storage.addFile("/file1.txt", 100);
        Optional<Integer> size = storage.getFileSize("/file1.txt");
        assertEquals(100, size.orElse(-1));
        assertEquals(Optional.empty(), storage.getFileSize("/nonexistent.txt"));
    }

    @Test
    void testAddUserAndAddFileByUser() {
        CloudStorage storage = new CloudStorageImpl();
        assertTrue(storage.addUser("user1", 500));
        Optional<Integer> remaining = storage.addFileBy("user1", "/user1/file.txt", 200);
        assertEquals(300, remaining.orElse(-1));
    }

    @Test
    void testCompressAndDecompressFile() {
        CloudStorage storage = new CloudStorageImpl();
        storage.addUser("user1", 1000);
        storage.addFileBy("user1", "/user1/file.txt", 400);

        Optional<Integer> remaining = storage.compressFile("user1", "/user1/file.txt");
        assertEquals(800, remaining.orElse(-1));

        remaining = storage.decompressFile("user1", "/user1/file.txt.COMPRESSED");
        assertEquals(600, remaining.orElse(-1));
    }
}
