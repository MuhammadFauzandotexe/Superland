package com.superland.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class VerifyRequest {
    private String token;
    private String name;
}
