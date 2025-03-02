package com.fst.il.m2.Projet.security;

import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, RuntimeException {

        // If it is the authentification request, don't check for token
        if(request.getRequestURI().equals("/api/users/authenticate")){
            filterChain.doFilter(request, response);
            return;
        }

        Cookie tokenCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token"))
                    .findFirst().orElse(null);
        }

        if (tokenCookie != null) {
            String token = tokenCookie.getValue();
            String username = jwtUtil.extractUsername(token);
            List<String> roles1 = jwtUtil.extractRoles(token);


            System.out.println("Extracted username: " + username);  // Debugging
            System.out.println("Extracted Roles: " + roles1);  // Debugging


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(token, username)) {
                    List<String> roles = jwtUtil.extractRoles(token);
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    System.out.println("Authentication set for user: " + username);  // Debugging

                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        // If the token is invalid or not present, return 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}