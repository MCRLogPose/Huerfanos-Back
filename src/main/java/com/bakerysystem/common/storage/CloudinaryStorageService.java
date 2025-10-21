package com.bakerysystem.common.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryStorageService implements CloudStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(MultipartFile file, String folderName) {
        try {
            System.out.println("subiendo imagen...");
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", folderName));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo a Cloudinary", e);
        }
    }

    @Override
    public void delete(String providerId) {
        try {
            cloudinary.uploader().destroy(providerId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar archivo en Cloudinary", e);
        }
    }
}
