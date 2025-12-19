package com.gt.planit.security.jwt;

import com.gt.planit.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /** ★ refresh 요청 및 login 요청은 JWT 검사 제외 */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        log.info("Requested Path: {}", path);  // 경로 로그 찍기

        // preflight 요청은 JWT 검사 제외
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }


//        String contextPath = request.getContextPath(); // "/api"
//
//        if (!contextPath.isEmpty()) {
//            path = path.substring(contextPath.length());
//        }

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

            String role = claims.get("role", String.class);  // claims에서 role 값 추출

            // role 값 로그로 찍기
            log.info("Role from token: {}", role);  // role이 제대로 꺼내졌는지 확인


            String username = claims.get("username", String.class);
            log.info("Username from token: {}", username);
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            var userDetails = userDetailsService.loadUserByUsername(username);
            log.info("UserDetails from token: {}", userDetails);

            // 여기서 CustomUserDetails의 권한을 로그로 찍음
            log.info("Role from CustomUserDetails: {}", userDetails.getAuthorities());  // 권한 확인


            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

            log.info(authToken.toString());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            
            log.info(authToken.toString());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 3️⃣ **SecurityContext**에서 권한 확인
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            log.info("Authorities from SecurityContext: {}", authorities);  // SecurityContext에 설정된 권한을 확인

            log.info("마지막까지 도달");
        } catch (Exception e) {
            log.error("JWT Authorization Filter Error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
