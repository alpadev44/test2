package com.example.Backend.Security;

import com.example.Backend.Model.DataTransferObject.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String jwt;
    private UserDTO userDTO;
}
