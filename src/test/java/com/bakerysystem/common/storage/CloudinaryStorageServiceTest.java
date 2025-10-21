package com.bakerysystem.common.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CloudinaryStorageServiceTest {

    private Cloudinary cloudinary;
    private Uploader uploader;
    private CloudinaryStorageService service;

    @BeforeEach
    void setUp() {
        cloudinary = mock(Cloudinary.class);
        uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);
        service = new CloudinaryStorageService(cloudinary);
    }

    @Test
    void testUploadImageSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fake-image".getBytes()
        );

        when(uploader.upload(any(), any()))
                .thenReturn(Map.of("secure_url", "http://mock.cloudinary.com/image.jpg"));

        String result = service.upload(file, "test-folder");

        assertEquals("http://mock.cloudinary.com/image.jpg", result);
        verify(uploader, times(1)).upload(any(), any());
    }

    @Test
    void testUploadImageThrowsRuntimeException() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fake-image".getBytes()
        );

        when(uploader.upload(any(), any())).thenThrow(new IOException("Error al subir archivo"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.upload(file, "test-folder");
        });

        assertTrue(thrown.getMessage().contains("Error al subir archivo a Cloudinary"));
    }
}
