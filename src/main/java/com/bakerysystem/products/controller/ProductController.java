package com.bakerysystem.products.controller;

import com.bakerysystem.common.storage.CloudStorageService;
import com.bakerysystem.products.dto.ProductRequestDTO;
import com.bakerysystem.products.dto.ProductResponseDTO;
import com.bakerysystem.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CloudStorageService storageService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductResponseDTO> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getBySku(sku));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable String sku, @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.update(sku, request));
    }

    @PatchMapping("/{sku}/stock")
    public ResponseEntity<ProductResponseDTO> addStock(@PathVariable String sku, @RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.addStock(sku, quantity));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> delete(@PathVariable String sku) {
        productService.delete(sku);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(@RequestParam String q) {
        return ResponseEntity.ok(productService.search(q));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String url = storageService.upload(file, "products");
        return ResponseEntity.ok(url);
    }
}
