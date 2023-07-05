package com.example.PharmacyWebApplication.PharmacyWebApplication.service;

import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.ProductDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Category;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setProductRating(productDto.getProductRating());
        product.setProductPrice(productDto.getProductPrice());
        product.setAvailable(productDto.isAvailable());
        product.setPrescriptionMed(productDto.isPrescriptionMed());
        product.setCategory(category);
        product.setQuantity(productDto.getQuantity());

        productRepository.save(product);
    }


    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setProductName(product.getProductName());
        productDto.setProductImageUrl(product.getProductImageUrl());
        productDto.setProductRating(product.getProductRating());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setAvailable(product.isAvailable());
        productDto.setPrescriptionMed(product.isPrescriptionMed());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        productDto.setQuantity(product.getQuantity());
        return productDto;
    }
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product:allProducts){
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }


    public void updateProduct(ProductDto productDto,Integer id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()){
            throw new Exception("product nor present");
        }
        Product product = optionalProduct.get();
        product.setProductName(productDto.getProductName());
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setProductRating(productDto.getProductRating());
        product.setProductPrice(productDto.getProductPrice());
        product.setAvailable(productDto.isAvailable());
        product.setPrescriptionMed(productDto.isPrescriptionMed());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);

    }
}

