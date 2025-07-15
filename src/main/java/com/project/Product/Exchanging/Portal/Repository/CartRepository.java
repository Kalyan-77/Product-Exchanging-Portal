package com.project.Product.Exchanging.Portal.Repository;

import com.project.Product.Exchanging.Portal.Model.Cart;
import com.project.Product.Exchanging.Portal.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUser(Users user);
    void deleteByUserAndProductId(Users user, Long productId);
    void deleteByUser(Users user);
}