package com.example.Backend.Controller;

import com.example.Backend.Model.DataTransferObject.UserDTO;
import com.example.Backend.Repository.IRoleRepository;
import com.example.Backend.Repository.IUserRepository;
import com.example.Backend.Security.AuthenticationRequest;
import com.example.Backend.Security.AuthenticationResponse;
import com.example.Backend.Security.JsonWebToken.IJwtUtil;
import com.example.Backend.Service.Implementation.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final IJwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            System.out.println("********************************");
            System.out.println("INGRESO AL TRY");
            System.out.println("********************************");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }
        catch(BadCredentialsException e) {
            System.out.println("********************************");
            System.out.println("INGRESO AL CATCH");
            System.out.println("********************************");
            throw new Exception("Incorrect credentials", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        UserDTO userDTO = userService.getUserByEmail(authRequest.getEmail()).get();
        AuthenticationResponse response = new AuthenticationResponse(jwt, userDTO);

        return ResponseEntity.ok(response);

    }

}