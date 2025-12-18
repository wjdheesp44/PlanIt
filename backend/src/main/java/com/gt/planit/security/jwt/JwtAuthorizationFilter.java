package com.gt.planit.security.jwt;

import com.gt.planit.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /** ★ refresh 요청 및 login 요청은 JWT 검사 제외 */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath(); // "/api"

        if (!contextPath.isEmpty()) {
            path = path.substring(contextPath.length());
        }

        return path.equals("/auth/refresh") || path.equals("/login")
                 || path.equals("/check-email") || path.equals("/check-nickname");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace("Bearer ", "");

        // 토큰 검증
        try {
            var claims = jwtUtil.getClaims(token);

            String username = claims.get("username", String.class);
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            var userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            log.error("JWT Authorization Filter Error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
