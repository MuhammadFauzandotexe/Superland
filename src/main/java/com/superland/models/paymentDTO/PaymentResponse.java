package com.superland.models.paymentDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("redirect_url")
    private String redirectUrl;
}
