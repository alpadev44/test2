package com.example.Backend.Model.DataTransferObject;

import com.example.Backend.Model.Entity.Category;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String url;
}
