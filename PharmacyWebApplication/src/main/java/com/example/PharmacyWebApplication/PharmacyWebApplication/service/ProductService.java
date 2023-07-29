package com.example.PharmacyWebApplication.PharmacyWebApplication.service;


import com.example.PharmacyWebApplication.PharmacyWebApplication.configuration.JwtRequestFilter;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.CartDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dao.UserDao;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.ProductDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Cart;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Category;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.User;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    FileService fileService;
    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public void createProduct(ProductDto productDto, Category category, MultipartFile file) throws IOException {
        String imageUrl;
        if (productDto.isPrescriptionMed()) {
            imageUrl = "https://storage.googleapis.com/shan-pharmacy-web-app-bucket/prescription_only.png";
        } else {
            if (file != null && !file.isEmpty()) {
                imageUrl = fileService.uploadFile(file);
            } else {
                imageUrl = null; // or set a default image URL for products without an image
            }
        }
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductImageUrl(imageUrl);
        product.setProductRating(productDto.getProductRating());
        product.setProductPrice(productDto.getProductPrice());
        product.setAvailable(productDto.isAvailable());
        product.setPrescriptionMed(productDto.isPrescriptionMed());
        product.setCategory(category);
        product.setQuantity(productDto.getQuantity());
        product.setExpDate(productDto.getExpDate());
        product.setThreshold(productDto.getThreshold());
        productRepository.save(product);
    }


    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setProductImageUrl(product.getProductImageUrl());
        productDto.setProductRating(product.getProductRating());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setAvailable(product.isAvailable());
        productDto.setPrescriptionMed(product.isPrescriptionMed());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        productDto.setQuantity(product.getQuantity());
        productDto.setExpDate(product.getExpDate());
        productDto.setThreshold(product.getThreshold());
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
        product.setExpDate(productDto.getExpDate());
        product.setThreshold(productDto.getThreshold());
        productRepository.save(product);

    }

    public void deleteProduct(int id) {
        Product product = productRepository.getReferenceById(id);
        productRepository.delete(product);

    }

    public boolean findById(int id) {
        return productRepository.findById(id).isPresent();
    }


    public ProductDto listProductById(int id) {
        Optional<Product> findById = productRepository.findById(id);
        if (!findById.isPresent()) {
            return null; // Or throw an exception indicating that the product with the given ID was not found
        }
        Product product = findById.get();
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setProductImageUrl(product.getProductImageUrl());
        productDto.setProductRating(product.getProductRating());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setAvailable(product.isAvailable());
        productDto.setPrescriptionMed(product.isPrescriptionMed());
        productDto.setQuantity(product.getQuantity());
        productDto.setExpDate(product.getExpDate());
        productDto.setThreshold(product.getThreshold());
        // Set other properties of the productDto based on the product entity

        return productDto;
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            list.add(product);
            return list;
        } else {
            // we are going to checkout entire cart
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(username).get();
            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }

    }
}

