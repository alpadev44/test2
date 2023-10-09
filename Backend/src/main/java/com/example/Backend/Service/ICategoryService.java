package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.CategoryDTO;
import com.example.Backend.Model.DataTransferObject.ImageDTO;
import com.example.Backend.Model.Entity.Category;
import com.example.Backend.Model.Entity.Image;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ICategoryService {
    void addCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> allCategories();
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id) throws NotFoundException;
    Optional<CategoryDTO> findCategory(Long id) throws NotFoundException;
    Category changeCategoryDTOToCategory(CategoryDTO categoryDTO);
    CategoryDTO changeCategoryToCategoryDTO(Category category);
}
