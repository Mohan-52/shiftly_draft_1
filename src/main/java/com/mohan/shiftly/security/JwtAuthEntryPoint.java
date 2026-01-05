package com.mohan.shiftly.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SignatureException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       Throwable cause= (Throwable) request.getAttribute("jwt_exception");

       if(cause!=null && cause.getMessage()!=null){
           cause=cause.getCause();
       }

       response.setContentType("application/json");
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

       if(cause instanceof ExpiredJwtException){
           response.getWriter().write("{\"message\": \"Jwt token expired}");
       } else if (cause instanceof SignatureException) {
           response.getWriter().write("{\"message\": \"Invalid Jwt Signature\"}");
       } else if (cause instanceof MalformedJwtException) {
           response.getWriter().write("{\"message\":\"Malformed Jwt Token\"}");
       } else if (cause!=null) {
           response.getWriter().write("{\"message\": \""+cause.getMessage()+"\"}");
       } else {
           response.getWriter().write("{\"message:\":\"Missing or Invalid Jwt Token\"}");
       }
    }
}
