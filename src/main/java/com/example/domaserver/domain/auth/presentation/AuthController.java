package com.example.domaserver.domain.auth.presentation;

import com.example.domaserver.domain.auth.presentation.dto.request.SignInRequest;
import com.example.domaserver.domain.auth.presentation.dto.response.TokenResponse;
import com.example.domaserver.domain.auth.serivce.LogOutService;
import com.example.domaserver.domain.auth.serivce.ReissueTokenService;
import com.example.domaserver.domain.auth.serivce.SignInService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignInService signInService;
    private final ReissueTokenService reissueTokenService;
    private final LogOutService logOutService;

    @PostMapping
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        TokenResponse response = signInService.execute(signInRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<TokenResponse> reissueToken(@RequestHeader String refreshToken) {
        TokenResponse tokenResponse = reissueTokenService.execute(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        logOutService.execute(request.getHeader("Authorization").substring(7));
        return ResponseEntity.noContent().build();
    }
}
