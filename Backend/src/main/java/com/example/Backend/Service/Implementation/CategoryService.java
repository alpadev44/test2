package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.CategoryDTO;
import com.example.Backend.Model.DataTransferObject.RoleDTO;
import com.example.Backend.Model.Entity.Category;
import com.example.Backend.Model.Entity.Role;
import com.example.Backend.Repository.ICategoryRepository;
import com.example.Backend.Service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = changeCategoryDTOToCategory(categoryDTO);
        iCategoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> allCategories() {
        List<Category> categoryList = iCategoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category categoryLists : categoryList) {
            categoryDTOList.add(changeCategoryToCategoryDTO(categoryLists));
        }

        return categoryDTOList;
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        changeCategoryToCategoryDTO(iCategoryRepository.save(changeCategoryDTOToCategory(categoryDTO)));
    }

    @Override
    public void deleteCategory(Long id) throws NotFoundException {
        Optional<Category> searchCategory = iCategoryRepository.findById(id);
        if(searchCategory.isPresent()) {
            iCategoryRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<CategoryDTO> findCategory(Long id) throws NotFoundException {
        Optional<Category> searchCategory = iCategoryRepository.findById(id);
        if(searchCategory.isPresent()) {
            return Optional.of(changeCategoryToCategoryDTO(searchCategory.get()));
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Category changeCategoryDTOToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setId(categoryDTO.getId());
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setUrl(categoryDTO.getUrl());

        return category;
    }

    @Override
    public CategoryDTO changeCategoryToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setUrl(category.getUrl());

        return categoryDTO;
    }
}
