package com.bakerysystem.products.service;

import com.bakerysystem.products.dto.ProductImageResponseDTO;
import com.bakerysystem.products.dto.ProductRequestDTO;
import com.bakerysystem.products.dto.ProductResponseDTO;
import com.bakerysystem.products.model.Category;
import com.bakerysystem.products.model.Product;
import com.bakerysystem.products.repository.CategoryRepository;
import com.bakerysystem.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageService productImageService;

    public ProductResponseDTO create(ProductRequestDTO request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .discountPercent(request.getDiscountPercent())
                .category(category)
                .active(true)
                .build();

        Product saved = productRepository.save(product);

        // Guardar las imágenes asociadas
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            // Aquí se asume que request.getImages() contiene solo URLs subidas a Cloudinary
            productImageService.saveMultiple(saved, request.getImages(), "cloudinary");
        }

        return toResponse(saved);
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(product);
    }

    public ProductResponseDTO update(String sku, ProductRequestDTO request) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setCategory(category);

        // Después de actualizar el producto
        Product updated = productRepository.save(product);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            // Por ejemplo, primero eliminar las antiguas y luego guardar las nuevas
            productImageService.deleteByProduct(updated);
            productImageService.saveMultiple(updated, request.getImages(), "cloudinary");
        }

        return toResponse(updated);
    }

    public ProductResponseDTO addStock(String sku, Integer quantity) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setStock(product.getStock() + quantity);
        return toResponse(productRepository.save(product));
    }

    public void delete(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setActive(false);
        productRepository.save(product);
    }

    public List<ProductResponseDTO> search(String query) {
        return productRepository.search(query).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO toResponse(Product p) {
        return ProductResponseDTO.builder()
                .sku(p.getSku())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .discountPercent(p.getDiscountPercent())
                .category(p.getCategory().getName())
                .images(
                        p.getImages() != null
                                ? p.getImages().stream()
                                .map(img -> ProductImageResponseDTO.builder()
                                        .url(img.getUrl())
                                        .altText(img.getAltText())
                                        .position(img.getPosition())
                                        .provider(img.getProvider())
                                        .build())
                                .toList()
                                : List.of()
                )
                .build();
    }
}