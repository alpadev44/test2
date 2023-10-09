package com.example.Backend.Model.DataTransferObject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private Integer score;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTimeProduct;
    private Set<ImageDTO> imageDTO;
    private List<MultipartFile> imagesFile;
    private Long categories_id;
    private CategoryDTO categoryDTO;
    private Long city_id;
    private CityDTO cityDTO;
    private Set<BookingDTO> bookingDTO = new HashSet<>();
}
