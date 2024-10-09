package com.buzz.controller;

import com.buzz.exception.ProductException;
import com.buzz.model.Product;
import com.buzz.request.CreateProductRequest;
import com.buzz.response.ApiResponse;
import com.buzz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        Product product= productService.createProduct(req);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){
        for(CreateProductRequest product:req){
            productService.createProduct(product);
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Product created Successfully!");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PutMapping("/{productId}/update")
    public  ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId) throws ProductException{
        Product product= productService.updateProduct(productId, req);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct (@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Product Deleted Successfully!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
