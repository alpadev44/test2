package com.example.Backend.Service;

import com.example.Backend.Exception.ResourceNotFoundException;
import com.example.Backend.Model.Entity.Product;

import java.util.Set;

public interface IFavoriteService {
    void addFavoriteProduct(Long userId, Long productId) throws ResourceNotFoundException;
    void removeFavoriteProduct(Long userId, Long productId) throws ResourceNotFoundException;
    Set<Product> allFavoritesProducts(Long userId) throws ResourceNotFoundException;
}
