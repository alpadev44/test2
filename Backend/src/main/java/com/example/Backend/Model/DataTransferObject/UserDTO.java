package com.example.Backend.Model.DataTransferObject;

import com.example.Backend.Model.Entity.Booking;

import com.example.Backend.Model.Entity.Role;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    //Este es para request
    private Long roles_id;
    //Este es para reponse
    private RoleDTO roleDTO;
    //private Set<Favorite> favorites = new HashSet<>();
    private Set<ProductDTO> productDTO = new HashSet<>();
    private Set<BookingDTO> bookingDTO = new HashSet<>();
}
