package com.project.Product.Exchanging.Portal.Service;

import com.project.Product.Exchanging.Portal.DTO.CartDTO;
import com.project.Product.Exchanging.Portal.Model.Cart;
import com.project.Product.Exchanging.Portal.Model.Products;
import com.project.Product.Exchanging.Portal.Model.Users;
import com.project.Product.Exchanging.Portal.Repository.CartRepository;
import com.project.Product.Exchanging.Portal.Repository.ProductRepository;
import com.project.Product.Exchanging.Portal.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Cart addToCart(Long userId, Long productId, int quantity) {
        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingCart.isPresent()) {
            // Update quantity if item already exists
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        } else {
            // Create new cart item
            Cart cart = new Cart();

            // Set user
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            cart.setUser(user);

            // Set product
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            cart.setProduct(product);

            cart.setQuantity(quantity);
            return cartRepository.save(cart);
        }
    }

    @Transactional
    public Cart updateCartQuantity(Long userId, Long productId, int newQuantity) {
        if (newQuantity <= 0) {
            deleteFromCart(userId, productId);
            return null;
        }

        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(userId, productId);
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setQuantity(newQuantity);
            return cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    public List<CartDTO> getUsersCart(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setProductId(cart.getProductId());
        dto.setQuantity(cart.getQuantity());

        // Get product details
        Products product = cart.getProduct();
        if (product != null) {
            CartDTO.ProductDetails productDetails = new CartDTO.ProductDetails();
            productDetails.setTitle(product.getTitle());
            productDetails.setLocation(product.getLocation());
            productDetails.setPrice(product.getPrice());
            productDetails.setDescription(product.getDescription());
            productDetails.setCategory(product.getCategory());
            productDetails.setCondition(product.getCondition());

            // Handle image URL
            String imageUrl = product.getImage();
            if (imageUrl != null && !imageUrl.startsWith("http")) {
                if (!imageUrl.startsWith("/uploads/")) {
                    imageUrl = "/uploads/" + imageUrl;
                }
            }
            productDetails.setImageUrl(imageUrl);

            // Get owner information
            if (product.getOwner() != null) {
                productDetails.setSeller(product.getOwner().getUsername());
            } else {
                productDetails.setSeller("Unknown Seller");
            }

            dto.setProduct(productDetails);
        }

        return dto;
    }

    @Transactional
    public void deleteFromCart(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}