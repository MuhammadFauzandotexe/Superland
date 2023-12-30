package com.superland.services;

import com.superland.models.CommonResponse;

public interface GameService {

    CommonResponse<?> scan(String id, String username);
    CommonResponse<?> getInfo(String id);
}
