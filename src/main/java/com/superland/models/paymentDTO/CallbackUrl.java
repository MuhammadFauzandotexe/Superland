package com.superland.models.paymentDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallbackUrl {

    @JsonProperty("finish")
    private String finish;
}
