package com.superland.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class VerifyResponse {
    private int statuscode;
    private String massage;
}
