package com.example.Backend.Controller;

import com.example.Backend.Exception.ResourceNotFoundException;
import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Service.Implementation.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {
    private FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<String> addFavoritesProduct(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) throws ResourceNotFoundException {
        favoriteService.addFavoriteProduct(userId,productId);
        return ResponseEntity.ok("Product added to favorite");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Set<Product>> allFavoritesProduct(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        Set<Product> favorites = favoriteService.allFavoritesProducts(userId);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> removeFavoritesProduct(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) throws ResourceNotFoundException {
        favoriteService.removeFavoriteProduct(userId,productId);
        return ResponseEntity.ok("Product removed to favorite");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
    }
}
