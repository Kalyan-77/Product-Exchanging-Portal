package com.project.Product.Exchanging.Portal.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER to avoid lazy loading issues
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    private int quantity = 1;

    // Helper methods to get IDs (for compatibility with existing service)
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    // Helper methods to set by ID (for service layer)
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new Users();
        }
        this.user.setId(userId);
    }

    public void setProductId(Long productId) {
        if (this.product == null) {
            this.product = new Products();
        }
        this.product.setId(productId);
    }
}