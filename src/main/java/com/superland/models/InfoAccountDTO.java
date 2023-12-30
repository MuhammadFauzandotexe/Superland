package com.superland.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class InfoAccountDTO {
    private Integer point;
    private String profileImage;
    private String username;
    private String qrImage;
    @Override
    public String toString() {
        return "InfoAccountDTO{" +
                "point=" + point +
                ", profileImage='" + profileImage + '\'' +
                ", username='" + username + '\'' +
                ", qrImage='" + qrImage + '\'' +
                '}';
    }


}
