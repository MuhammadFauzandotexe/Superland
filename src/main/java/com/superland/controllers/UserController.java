package com.superland.controllers;

import com.superland.services.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }
}
