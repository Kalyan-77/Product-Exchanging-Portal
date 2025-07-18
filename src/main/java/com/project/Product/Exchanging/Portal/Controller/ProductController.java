package com.project.Product.Exchanging.Portal.Controller;


import com.project.Product.Exchanging.Portal.Model.Products;
import com.project.Product.Exchanging.Portal.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Products> createProduct(
            @RequestBody Products products,
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(productService.createProduct(products, userId));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Products>> getAllProducts(){
        return  ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Products> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/user/{userid}")
//    public ResponseEntity<List<Products>> getProductsByOwner(@PathVariable Long userId){
//        return   ResponseEntity.ok(productService.getProductByOwner(userId));
//    }

    @GetMapping("/search/category")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Products>> searchByCategory(@RequestParam String category){
        return ResponseEntity.ok(productService.searchByCategory(category));
    }

    @GetMapping("/search/title")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Products>> searchByTitle(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchByTitle(keyword));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products products){
        return ResponseEntity.ok(productService.updateProduct(id, products));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return  ResponseEntity.noContent().build();
    }

}