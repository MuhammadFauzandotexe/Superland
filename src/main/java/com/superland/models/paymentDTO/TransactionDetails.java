package com.superland.models.paymentDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionDetails {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("gross_amount")
    private Integer grossAmount;
}
