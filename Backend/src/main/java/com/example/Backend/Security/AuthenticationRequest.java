package com.example.Backend.Security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}

/*
1. Crear El rol en la base de datos ( data config )
2. Crear el user Alejandro ( data config )
3. Cuando llegue un Json tiene un authenticationRequest de ahi va a authentication controller e inicia ciclo de verificacion.
 */