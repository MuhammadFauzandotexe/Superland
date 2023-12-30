package com.superland.controllers;


import com.superland.entity.UserCredentials;
import com.superland.models.LoginResponseDTO;
import com.superland.models.RegistrationDTO;
import com.superland.models.VerifyRequest;
import com.superland.models.VerifyResponse;
import com.superland.services.impl.AuthenticationService;
import com.superland.services.impl.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
//@AllArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserCredentials registerUser(@RequestBody RegistrationDTO body) throws IOException {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
    
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
    @PostMapping("/verify")
    public VerifyResponse verificationUser(@RequestBody VerifyRequest request){
        request.setName("");
        return authenticationService.verifyUser(request.getToken());
    }
}   
