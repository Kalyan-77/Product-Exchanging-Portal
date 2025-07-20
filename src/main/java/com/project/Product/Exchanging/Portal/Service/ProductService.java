package com.project.Product.Exchanging.Portal.Service;


import com.project.Product.Exchanging.Portal.Model.Products;
import com.project.Product.Exchanging.Portal.Model.Users;
import com.project.Product.Exchanging.Portal.Repository.ProductRepository;
import com.project.Product.Exchanging.Portal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public Products createProduct(Products products, Long id) {
        Users owner = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        products.setOwner(owner);
        products.setCreatedAt(LocalDateTime.now());
        return productRepository.save(products);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Products> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Products> getProductByOwner(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found "));
        return productRepository.findByOwner(users);
    }

    public List<Products> searchByCategory(String category) {
        return productRepository.findByCategoryContainingIgnoreCase(category);  // Fixed
    }

    public List<Products> searchByTitle(String keyword) {
        return productRepository.findByTitleContainingIgnoreCase(keyword);      // Fixed
    }


    public Products updateProduct(Long id, Products updatedProduct) {
        return productRepository.findById(id).map(products -> {
            products.setTitle(updatedProduct.getTitle());
            products.setDescription(updatedProduct.getDescription());
            products.setImage(updatedProduct.getImage());
            products.setCategory(updatedProduct.getCategory());
            products.setPrice(updatedProduct.getPrice());
            products.setCondition(updatedProduct.getCondition());
            products.setLocation(updatedProduct.getLocation());
            products.setNumber(updatedProduct.getNumber());
            products.setEmail(updatedProduct.getEmail());
            products.setMessage(updatedProduct.getMessage());
            return productRepository.save(products);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
