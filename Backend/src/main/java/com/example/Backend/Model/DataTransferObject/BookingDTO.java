package com.example.Backend.Model.DataTransferObject;

import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Model.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_date;
    private LocalDateTime finish_date;
    private Long products_id;
    private Set<ProductDTO> productDTO;
    private Long users_id;
    private Set<UserDTO> userDTO;
}
