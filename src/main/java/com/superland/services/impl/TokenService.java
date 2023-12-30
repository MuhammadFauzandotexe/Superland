package com.superland.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class TokenService {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String generateJwt(Authentication auth){
        Instant now = Instant.now();
        String scope = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .subject(auth.getName())
            .claim("roles", scope)
            .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    public String getUserName(String jwtToken){
        Jwt jwt = jwtDecoder.decode(jwtToken);
        return jwt.getSubject();
    }
    //https://d85c-103-78-25-190.ngrok-free.app
}
