package com.project.Product.Exchanging.Portal.DTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String category;
    private Double price;
    private LocalDateTime createdAt;
    private Long ownerId;
    private String ownerName; // Optional, if you want to show who posted the product
}
