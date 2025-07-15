package com.project.Product.Exchanging.Portal.Controller;

import com.project.Product.Exchanging.Portal.Model.Cart;
import com.project.Product.Exchanging.Portal.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity){
        return ResponseEntity.ok(cartService.addToCart(userId,productId,quantity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getUsersCart(userId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> DeleteCart(
            @RequestParam Long userId,
            @RequestParam Long productId){
        cartService.DeleteFromCart(userId,productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}