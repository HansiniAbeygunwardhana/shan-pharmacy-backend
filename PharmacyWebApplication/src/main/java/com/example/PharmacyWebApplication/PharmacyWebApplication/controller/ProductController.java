package com.example.PharmacyWebApplication.PharmacyWebApplication.controller;

import com.example.PharmacyWebApplication.PharmacyWebApplication.common.ApiResponse;
import com.example.PharmacyWebApplication.PharmacyWebApplication.dto.ProductDto;
import com.example.PharmacyWebApplication.PharmacyWebApplication.model.Category;
import com.example.PharmacyWebApplication.PharmacyWebApplication.repository.CategoryRepository;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.FileService;
import com.example.PharmacyWebApplication.PharmacyWebApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    FileService fileService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@ModelAttribute ProductDto productDto, @RequestParam("file") MultipartFile file){
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category does not exist"), HttpStatus.NOT_FOUND);
        }
        try {
            String imageUrl = fileService.uploadFile(file);
            productDto.setProductImageUrl(imageUrl);

            productService.createProduct(productDto, optionalCategory.get(), file);
            return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error uploading image"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<ProductDto> listProductById(@PathVariable int id){

        ProductDto viewById=productService.listProductById(id);
        return new ResponseEntity<ProductDto>(viewById,HttpStatus.OK);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") int id) {
        if (!productService.findById(id)) {
            return new ResponseEntity<>(new ApiResponse(false, "Product does not exist"), HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been deleted"), HttpStatus.OK);
    }


}
