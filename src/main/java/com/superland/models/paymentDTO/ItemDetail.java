package com.superland.models.paymentDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDetail {
    @JsonProperty("id")
    private String id;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("name")
    private String name;
}
