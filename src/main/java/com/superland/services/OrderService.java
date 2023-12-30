package com.superland.services;

import com.superland.models.CommonResponse;

public interface OrderService {
    CommonResponse<?> requestRequest(String username,Integer point);

}
