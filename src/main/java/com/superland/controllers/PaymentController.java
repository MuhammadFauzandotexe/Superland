package com.superland.controllers;

import com.superland.models.CommonResponse;
import com.superland.services.OrderService;
import com.superland.services.impl.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
//@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderService orderService;
    @PostMapping("/request/{price}")
    public CommonResponse<?> request(
            @PathVariable Integer price,
            HttpServletRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = tokenService.getUserName(token);
            return orderService.requestRequest(username,price);
        }
        return null;
    }
}
