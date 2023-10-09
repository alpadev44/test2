package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.ResourceNotFoundException;
import com.example.Backend.Model.DataTransferObject.ProductDTO;
import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Model.Entity.User;
import com.example.Backend.Repository.IProductRepository;
import com.example.Backend.Repository.IUserRepository;
import com.example.Backend.Service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FavoriteService implements IFavoriteService {

    private IUserRepository userRepository;
    private IProductRepository productRepository;

    @Autowired
    public FavoriteService(IUserRepository userRepository, IProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addFavoriteProduct(Long userId, Long productId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        user.addFavoriteProduct(product);
        userRepository.save(user);
    }

    @Override
    public void removeFavoriteProduct(Long userId, Long productId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        user.removeFavoriteProduct(product);
        userRepository.save(user);
    }

    @Override
    public Set<Product> allFavoritesProducts(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return user.getFavoriteProduct();
    }
}
