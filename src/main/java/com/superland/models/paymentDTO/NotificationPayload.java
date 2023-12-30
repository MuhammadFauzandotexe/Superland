package com.superland.models.paymentDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationPayload {
    private String transaction_status;
    private String order_id;
}
