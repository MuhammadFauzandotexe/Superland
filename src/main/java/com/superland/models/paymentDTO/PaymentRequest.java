package com.superland.models.paymentDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class PaymentRequest {
    @JsonProperty("transaction_details")
    private TransactionDetails transactionDetails;

    @JsonProperty("item_details")
    private List<ItemDetail> itemDetails;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    @JsonProperty("expiry")
    private PageExpire pageExpire;
    @JsonProperty("callbacks")
    private CallbackUrl callbackUrl;
}

