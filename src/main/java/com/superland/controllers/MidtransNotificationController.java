package com.superland.controllers;

import com.superland.entity.Orders;
import com.superland.entity.UserAccounts;
import com.superland.models.paymentDTO.NotificationPayload;
import com.superland.repository.AccountRepository;
import com.superland.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/notification")
@Slf4j
//@AllArgsConstructor
public class MidtransNotificationController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/handling")
    public ResponseEntity<String> handleGenericNotification(@RequestBody NotificationPayload payload) {
        log.info("Transaction Status : "+payload.getTransaction_status());
        log.info("Order ID : "+payload.getOrder_id());
        if(payload.getTransaction_status().equals("settlement")){
            Optional<Orders> order = orderRepository.findById(payload.getOrder_id());
            if (order.isPresent()){
                order.get().setStatus(payload.getTransaction_status());
                Optional<UserAccounts> accounts = accountRepository.findById(order.get().getAccount().getUserId());
                if (accounts.isPresent()){
                    accounts.get().setPoint(accounts.get().getPoint()+order.get().getPoints());
                    accountRepository.save(accounts.get());
                    log.info("Already paid "+accounts.get().getUserId()+" for order "+order.get().getId());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Notification received");
    }
}
