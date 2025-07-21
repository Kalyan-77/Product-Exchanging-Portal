package com.project.Product.Exchanging.Portal.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;

    // Product details with correct field names to match frontend
    private ProductDetails product;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetails {
        private String title;
        private String seller; // Owner's name
        private String location;
        private Double price;
        private String imageUrl; // Image URL
        private String description;
        private String category;
        private String condition;
        private String Owner;
    }
}