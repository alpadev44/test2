package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.ProductDTO;
import com.example.Backend.Model.DataTransferObject.UserDTO;
import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Model.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface IProductService {
    void addProduct(ProductDTO  productDTO);
    Set<ProductDTO> allProducts();
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(Long id) throws NotFoundException;
    Optional<ProductDTO> findProduct(Long id) throws NotFoundException;
    Product changeProductDTOToProduct(ProductDTO productDTO);
    ProductDTO changeProductToProductDTO(Product product);
}
