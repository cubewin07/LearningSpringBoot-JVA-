package org.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user_service.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.MDC;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authenHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        long timestamp = System.currentTimeMillis();

        String path = request.getServletPath();
        String method = request.getMethod();

        log.info("[Request] {} {} ({} ms)", path, method, timestamp);
        log.debug("Authorization header: {}", authenHeader);

        if(authenHeader == null || !authenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            log.info("[Response] method={} path={} status={} duration={}ms",
                    method, path, response.getStatus(), timestamp);
            return;
        }

        try {
            jwt = authenHeader.substring(7);
            username = jwtService.extractUsername(jwt);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtService.isValidToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    var user = (User) userDetails;
                    MDC.put("userId", user.getId().toString());
                    log.info("Authenticated user: {}", username);
                } else {
                    log.warn("Invalid JWT token for user: {}", username);
                }
            }
            Long duration = System.currentTimeMillis() - timestamp;
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
            long duration = System.currentTimeMillis() - timestamp;
            log.info("[Response] method={} path={} status={} duration={}ms",
                    method, path, response.getStatus(), duration);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/actuator");
    }
}
