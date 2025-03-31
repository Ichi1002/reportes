package com.leo.reportes.infrastructure.security.endpoints;

import com.leo.reportes.infrastructure.security.dtos.AuthRequestDTO;
import com.leo.reportes.infrastructure.security.dtos.JwtResponseDTO;
import com.leo.reportes.infrastructure.security.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response){
        var auth = new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(auth);
        if(authentication.isAuthenticated()){
            //RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    //.token(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }
}
