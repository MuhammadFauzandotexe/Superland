package com.superland.services.impl;


import com.superland.entity.UserAccounts;
import com.superland.entity.UserCredentials;
import com.superland.models.LoginResponseDTO;
import com.superland.entity.Role;
import com.superland.models.VerifyResponse;
import com.superland.repository.AccountRepository;
import com.superland.repository.RoleRepository;
import com.superland.repository.UserRepository;
import com.superland.utils.EmailService;
import com.superland.utils.QRCodeGeneratorService;
import com.superland.utils.RandomTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
//@AllArgsConstructor
public class AuthenticationService {

//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final TokenService tokenService;
//    private final EmailService emailService;
//    private final AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountRepository accountRepository;

    public UserCredentials registerUser(String username, String password) throws IOException {
        String token  = RandomTokenGenerator.generateRandomToken(5);
        Context context = new Context();
        context.setVariable("username",username);
        context.setVariable("token",token);
        try {
            emailService.sendEmail(username,"Verifikasi Akun Superland Anda","email",context);
            String encodedPassword = passwordEncoder.encode(password);
            Role userRole = roleRepository.findByAuthority("USER").get();
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);
            return userRepository.saveAndFlush(new UserCredentials(username, encodedPassword, authorities, false, token));
        }
        catch (Exception e){
            throw new HttpTimeoutException("Email Not Found");
        }
    }

    public LoginResponseDTO loginUser(String username, String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

    public VerifyResponse verifyUser(String token){
        try {
            Optional<UserCredentials> userCredentials = userRepository.findByTokenVerification(token);
            if (userCredentials.isPresent()){
                userCredentials.get().setActive(true);
                UserAccounts userAccounts = new UserAccounts();
                userAccounts.setPoint(100);
                userAccounts.setStatusMember("Free_Member");
                userAccounts.setProfileImage("default.jpg");
                userAccounts.setQrImage(userCredentials.get().getUserId()+".jpg");
                userAccounts.setCredential(userCredentials.get());
                QRCodeGeneratorService.generate(userCredentials.get().getUserId(),userCredentials.get().getUserId());
                accountRepository.saveAndFlush(userAccounts);
                return VerifyResponse.builder().statuscode(HttpStatus.CONTINUE.value()).massage("Success").build();
            }
            else {
                throw new UsernameNotFoundException("Invalid Token");
            }
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
