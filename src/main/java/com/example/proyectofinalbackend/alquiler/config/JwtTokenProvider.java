package com.example.proyectofinalbackend.alquiler.config;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "your-secret-key";  // Debes usar una clave secreta segura

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Maneja la excepción adecuadamente (token expirado, mal formato, etc.)
            return false;
        }
    }

    // Método para obtener el Authentication del token
    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();  // Obtener el nombre de usuario desde el token

        UserDetails userDetails = new User(username, "jose", Collections.emptyList()); // Recuperar UserDetails (vacío en este ejemplo)
        return new UsernamePasswordAuthenticationToken(userDetails, "12345", userDetails.getAuthorities());
    }

    // Método para obtener las claims del token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }
}