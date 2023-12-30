package com.superland.models;

import com.superland.entity.UserCredentials;

public class LoginResponseDTO {
    private UserCredentials user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(UserCredentials user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public UserCredentials getUser(){
        return this.user;
    }

    public void setUser(UserCredentials user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
