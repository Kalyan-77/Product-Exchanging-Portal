package com.project.Product.Exchanging.Portal.Service;

import com.project.Product.Exchanging.Portal.Model.Cart;
import com.project.Product.Exchanging.Portal.Model.Products;
import com.project.Product.Exchanging.Portal.Model.Users;
import com.project.Product.Exchanging.Portal.Repository.CartRepository;
import com.project.Product.Exchanging.Portal.Repository.ProductRepository;
import com.project.Product.Exchanging.Portal.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(Long userId, Long productId, int quantity){
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Products products = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = new Cart();
        cart.setUser(users);
        cart.setProduct(products);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    public List<Cart> getUsersCart(Long userId){
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(users);
    }

    @Transactional
    public void DeleteFromCart(Long userId, Long productId){
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        cartRepository.deleteByUserAndProductId(users,productId);
    }

    public void clearCart(Long userId){
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> items = cartRepository.findByUser(user);
        cartRepository.deleteAll(items);
    }
}
