package com.bakerysystem.products.service;

import com.bakerysystem.products.model.Product;
import com.bakerysystem.products.model.ProductImage;
import com.bakerysystem.products.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImage saveImage(Product product, String url, String provider, String altText, Integer position) {
        ProductImage image = ProductImage.builder()
                .product(product)
                .url(url)
                .provider(provider)
                .altText(altText)
                .position(position)
                .build();
        return productImageRepository.save(image);
    }

    public List<ProductImage> saveMultiple(Product product, List<String> urls, String provider) {
        List<ProductImage> images = new ArrayList<>();
        int position = 0;
        for (String url : urls) {
            images.add(ProductImage.builder()
                    .product(product)
                    .url(url)
                    .provider(provider)
                    .altText(product.getName())
                    .position(position++)
                    .build());
        }
        return productImageRepository.saveAll(images);
    }

    public void deleteByProduct(Product product) {
        productImageRepository.deleteAll(product.getImages());
    }
}
