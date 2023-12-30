package com.superland.models.paymentDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageExpire {
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("duration")
    private Integer duration;
}
