package com.superland.services;

import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.models.CommonResponse;
import com.superland.models.InfoAccountDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AccountsService {
    InfoAccountDTO getInfo(String username);
    CommonResponse<String> setProfilePicture(String username, MultipartFile file);
    Optional<UserAccounts> findByCredential(UserCredentials userCredentials);
}
