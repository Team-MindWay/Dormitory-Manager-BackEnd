package com.example.domaserver.domain.auth.serivce;

import com.example.domaserver.domain.auth.presentation.dto.response.TokenResponse;

public interface ReissueTokenService {
    TokenResponse execute(String refreshToken);
}
