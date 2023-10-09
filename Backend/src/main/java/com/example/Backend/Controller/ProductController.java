package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.ProductDTO;
import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Service.Implementation.AwsS3Service;
import com.example.Backend.Service.Implementation.BookingService;
import com.example.Backend.Service.Implementation.ProductService;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AwsS3Service awsS3Service;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        URI uri = URI.create("/products/" + productDTO.getId());
        return ResponseEntity.created(uri).body(productDTO);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allProducts")
    public Set<ProductDTO> allProducts() { return productService.allProducts(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws NotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return ResponseEntity.ok(productDTO);
    }

 //   @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findProduct/{id}")
    public ResponseEntity<?> findProduct(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAvailableDatesForProduct")
    public ResponseEntity<List<LocalDateTime>> getAvailableDatesForProduct(Product product) {
        return ResponseEntity.ok(productService.getAvailableDatesForProduct(product));
    }


}