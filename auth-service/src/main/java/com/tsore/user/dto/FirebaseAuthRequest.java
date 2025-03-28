package com.tsore.user.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FirebaseAuthRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
