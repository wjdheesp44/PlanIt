package com.gt.planit.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.planit.security.dto.LoginReqDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            LoginReqDto loginRequest =
                    objectMapper.readValue(
                            request.getReader(),
                            LoginReqDto.class
                    );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            log.info("로그인 요청 JSON 파싱 실패 {}",e);
            throw new RuntimeException("로그인 요청 JSON 파싱 실패", e);
        }
    }


    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            jakarta.servlet.FilterChain chain,
            Authentication authResult) throws IOException {

        String username = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority();

        String access = jwtUtil.createAccessToken(username, role);
        String refresh = jwtUtil.createRefreshToken(username);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),
                Map.of("accessToken", access, "refreshToken", refresh));
    }
}
