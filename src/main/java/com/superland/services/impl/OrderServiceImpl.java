package com.superland.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superland.configuration.AppConfiguration;
import com.superland.entity.Orders;
import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.models.CommonResponse;
import com.superland.models.paymentDTO.*;
import com.superland.repository.AccountRepository;
import com.superland.repository.OrderRepository;
import com.superland.repository.UserRepository;
import com.superland.services.AccountsService;
import com.superland.services.OrderService;
import com.superland.utils.ExpiryPaymentLinkUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
//@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
//    private final RestTemplate restTemplate;
//    private final UserRepository userRepository;
//    private final AccountsService accountsService;
//    private final OrderRepository orderRepository;
//    private final AppConfiguration configuration;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountsService accountsService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AppConfiguration configuration;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public CommonResponse<?> requestRequest(String username,Integer point) {
        Optional<UserCredentials> userCredentialsOptional = userRepository.findByUsername(username);
        if(userCredentialsOptional.isPresent()){
            Optional<UserAccounts> userAccounts = accountsService.findByCredential(userCredentialsOptional.get());
            if (userAccounts.isPresent()){
                Integer grossAmount = point*100;
                Integer quantity = 1;
                Orders orders = new Orders();
                orders.setAccount(userAccounts.get());
                orders.setGrossAmount(grossAmount);
                orders.setPoints(point);
                Orders orders1 = orderRepository.saveAndFlush(orders);

                PaymentRequest paymentRequest = new PaymentRequest();
                TransactionDetails transactionDetails = new TransactionDetails();
                transactionDetails.setGrossAmount(grossAmount*quantity);
                transactionDetails.setOrderId(orders1.getId());

                ItemDetail itemDetail = new ItemDetail();
                itemDetail.setId("SP001");
                itemDetail.setName("Superland Point");
                itemDetail.setQuantity(quantity);
                itemDetail.setPrice(grossAmount*quantity);

                CustomerDetails customerDetails = new CustomerDetails();
                customerDetails.setEmail(userCredentialsOptional.get().getUsername());
                PageExpire pageExpire = new PageExpire();
                CallbackUrl callbackUrl = new CallbackUrl();
                callbackUrl.setFinish(configuration.getCallBackUrl());
                pageExpire.setStartTime(ExpiryPaymentLinkUtil.generate());
                pageExpire.setUnit("minutes");
                pageExpire.setDuration(10);
                paymentRequest.setTransactionDetails(transactionDetails);
                paymentRequest.setItemDetails(List.of(itemDetail));
                paymentRequest.setCustomerDetails(customerDetails);
                paymentRequest.setPageExpire(pageExpire);
                paymentRequest.setCallbackUrl(callbackUrl);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", configuration.getMidtransServerKey());

                try {
                    HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
                    ResponseEntity<String> response = restTemplate.postForEntity(configuration.getMidtransApiUrl(), requestEntity, String.class);
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    PaymentResponse responseData = objectMapper.readValue(responseBody, PaymentResponse.class);

                    return CommonResponse.<PaymentResponse>builder()
                            .statusCode(HttpStatus.OK.value())
                            .data(responseData)
                            .message("Success create payment")
                            .build();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return CommonResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
