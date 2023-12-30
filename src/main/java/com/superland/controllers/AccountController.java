package com.superland.controllers;

import com.superland.models.CommonResponse;
import com.superland.models.InfoAccountDTO;
import com.superland.services.AccountsService;
import com.superland.services.impl.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/account")
@CrossOrigin("*")
//@AllArgsConstructor
public class AccountController {
    @Autowired

    private TokenService tokenService;
    @Autowired
    private AccountsService accountsService;
    @GetMapping("/about-me")
    public InfoAccountDTO checkBearerToken(HttpServletRequest request, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = tokenService.getUserName(token);
        return accountsService.getInfo(username);
        }
        return null;
    }
    @PostMapping("/set-profile-picture")
    public CommonResponse<String> setProfilePicture(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = tokenService.getUserName(token);
            return accountsService.setProfilePicture(username, file);
        }
        return CommonResponse.<String>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Fail  to update").build();
    }
}
