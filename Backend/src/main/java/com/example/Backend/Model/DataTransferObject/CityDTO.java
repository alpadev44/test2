package com.example.Backend.Model.DataTransferObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {
    private Long id;
    private String name;
    private String country;
    private Long address_id;
    private AddressDTO addressDTO;

}
