package com.superland.services.impl;

import com.google.zxing.NotFoundException;
import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.models.CommonResponse;
import com.superland.models.InfoAccountDTO;
import com.superland.repository.AccountRepository;
import com.superland.repository.UserRepository;
import com.superland.services.AccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
public class AccountsServiceImpl implements AccountsService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AccountRepository accountRepository;
    @Override
    public InfoAccountDTO getInfo(String username) {
        Optional<UserCredentials> userCredentialsOptional = userRepository.findByUsername(username);
        if(userCredentialsOptional.isPresent()){
            Optional<UserAccounts> userAccounts = accountRepository.findByCredential(userCredentialsOptional.get());
            if (userAccounts.isPresent()){
                return InfoAccountDTO.builder()
                        .point(userAccounts.get().getPoint())
                        .profileImage(userAccounts.get().getProfileImage())
                        .qrImage(userAccounts.get().getQrImage())
                        .username(userCredentialsOptional.get().getUsername())
                        .build();
            }
        }
        return null;
    }

    @Override
    public CommonResponse<String> setProfilePicture(String username, MultipartFile file) {
        Optional<UserCredentials> userCredentialsOptional = userRepository.findByUsername(username);
        if(userCredentialsOptional.isPresent()){
            Optional<UserAccounts> userAccounts = accountRepository.findByCredential(userCredentialsOptional.get());
            if (userAccounts.isPresent()){
                String contentType = file.getContentType();
                assert contentType != null;
                String[] contentTypeParts = contentType.split("/");
                String fileExtension = contentTypeParts[1];
                Path path = Paths.get("D:/DEV/Mini-Project/frontend/src/assets/images/profilePicture/",userAccounts.get().getCredential().getUserId()+"."+fileExtension);
                try {
                    Files.write(path, file.getBytes());
                    userAccounts.get().setProfileImage(userAccounts.get().getCredential().getUserId()+"."+fileExtension);
                    accountRepository.save(userAccounts.get());
                    return CommonResponse.<String>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Success update profile")
                            .build();
                }catch (IOException e){
                    log.info(e.getMessage());
                }
            }
        }
        return CommonResponse.<String>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Fail update profile")
                .build();
    }
    @Override
    public Optional<UserAccounts> findByCredential(UserCredentials userCredentials) {
        return Optional.ofNullable(accountRepository.findByCredential(userCredentials)
                .orElseThrow(() -> new RuntimeException("Akun tidak ditemukan")));
    }
}
