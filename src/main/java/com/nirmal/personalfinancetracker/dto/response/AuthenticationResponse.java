package com.nirmal.personalfinancetracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
//    private String username;
    private String accessToken;
    private String refreshToken;
}
