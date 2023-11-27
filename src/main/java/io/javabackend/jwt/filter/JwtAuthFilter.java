package io.javabackend.jwt.filter;

import io.javabackend.entity.User;
import io.javabackend.jwt.service.JwtService;
//import io.javabackend.jwt.service.UserInfoService;
import io.javabackend.jwt.service.UserInfoDetails;
import io.javabackend.jwt.service.UserInfoService;
import io.javabackend.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

// This class helps us to validate the generated jwt token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired

    private JwtService jwtService;
    @Autowired
    private UserInfoService userInfoService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = null;


        // Validate from cookie
        token = this.extractJwtTokenFromCookie(request);

        if (token != null) {
            username = jwtService.extractUsername(token);

            UserDetails userInfoDetails = userInfoService.loadUserByUsername(username);


            if (jwtService.validateToken(token, userInfoDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userInfoDetails, null, userInfoDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//
//            System.out.println(username);
//        }
        filterChain.doFilter(request, response);
    }


    public String extractJwtTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // Use Java Stream API to find the "jwt" cookie
            Cookie jwtCookie = Arrays.stream(cookies)
                    .filter(cookie -> "jwt".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            if (jwtCookie != null) {
                return jwtCookie.getValue();
            }
        }

        return null; // Return null if the "jwt" cookie is not found
    }   ;
}
