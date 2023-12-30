package com.superland.services.impl;

import com.superland.entity.Games;
import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.models.CommonResponse;
import com.superland.repository.AccountRepository;
import com.superland.repository.GameRepository;
import com.superland.repository.UserRepository;
import com.superland.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class GameServiceImpl implements GameService {
    @Autowired
    private  GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public CommonResponse<?> scan(String id, String username) {
        Optional<Games> game = gameRepository.findById(id);
        if (game.isPresent()){
            Optional<UserCredentials> userCredentialsOptional = userRepository.findByUsername(username);
            if(userCredentialsOptional.isPresent()){
                Optional<UserAccounts> userAccounts = accountRepository.findByCredential(userCredentialsOptional.get());
                userAccounts.ifPresent(accounts -> {
                    accounts.setPoint(accounts.getPoint() - game.get().getPointPrice());
                });
                accountRepository.save(userAccounts.get());
                return CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .data(game)
                        .build();
            }
            return CommonResponse.builder()
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .message("Unauthorized")
                    .build();
        }
        return CommonResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("NotFound")
                .build();
    }

    @Override
    public CommonResponse<?> getInfo(String id) {
        Optional<Games> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return CommonResponse.builder()
                    .statusCode(HttpStatus.FOUND.value())
                    .message("Success")
                    .data(game)
                    .build();

        }

         return CommonResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Not-Found")
                .data(null)
                .build();
    }
}
