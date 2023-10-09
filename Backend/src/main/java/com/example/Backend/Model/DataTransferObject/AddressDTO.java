package com.example.Backend.Model.DataTransferObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
}
