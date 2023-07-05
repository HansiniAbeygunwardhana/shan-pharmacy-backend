package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.common.ApiResponse;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.ProductDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Category;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Product;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.CategoryRepository;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.createProduct(productDto,optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true,"product has been added"),HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") int id ,@RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"product does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto,id);
        return new ResponseEntity<>(new ApiResponse(true,"product has been updated"),HttpStatus.OK
        );}
}
