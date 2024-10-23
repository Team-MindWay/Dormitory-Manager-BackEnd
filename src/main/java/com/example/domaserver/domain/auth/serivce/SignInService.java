package com.example.domaserver.domain.auth.serivce;

import com.example.domaserver.domain.auth.presentation.dto.request.SignInRequest;
import com.example.domaserver.domain.auth.presentation.dto.response.TokenResponse;

public interface SignInService {
    TokenResponse execute(SignInRequest signInRequest);
}
