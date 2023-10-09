package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.CategoryDTO;
import com.example.Backend.Service.Implementation.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        URI uri = URI.create("/categories/" + categoryDTO.getId());
        return ResponseEntity.created(uri).body(categoryDTO);
    }

 //   @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allCategories")
    public List<CategoryDTO> allCategories() { return categoryService.allCategories(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateCategory")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findCategory/{id}")
    public ResponseEntity<?> findCategory(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(categoryService.findCategory(id));
    }
}
