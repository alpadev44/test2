package com.example.Backend.Model.DataTransferObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    private String destinatario;
    private String asunto;
    private String contenido;
}
