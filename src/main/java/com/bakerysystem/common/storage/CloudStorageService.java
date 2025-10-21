package com.bakerysystem.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageService {
    String upload(MultipartFile file, String folderName);
    void delete(String providerId);
}