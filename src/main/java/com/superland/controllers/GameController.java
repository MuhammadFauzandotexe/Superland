package com.superland.controllers;

import com.superland.models.CommonResponse;
import com.superland.services.GameService;
import com.superland.services.impl.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/game")
//@AllArgsConstructor
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private TokenService tokenService;
    @GetMapping("/scan/{id}")
    public ResponseEntity<CommonResponse<?>> scan(
            @PathVariable String id,
            HttpServletRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = tokenService.getUserName(token);
            CommonResponse<?> scan = gameService.scan(id, username);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(scan);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }    @GetMapping("/get-info/{id}")
    public ResponseEntity<CommonResponse<?>> getInfo(
            @PathVariable String id,
            HttpServletRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = tokenService.getUserName(token);
            CommonResponse<?> scan = gameService.getInfo(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(scan);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
