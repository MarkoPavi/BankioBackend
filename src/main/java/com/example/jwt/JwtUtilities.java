package com.example.jwt;


import com.example.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtUtilities {


    @Value("${bankio.app.jwtSecret}")
    private String jwtSecret;


    public String generateToken(Authentication authentication) throws ParseException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LocalDateTime expirationLocalDate = LocalDateTime.now().plus(30, ChronoUnit.MINUTES);
        Date expDate = Date.from(expirationLocalDate.atZone(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(("Invalid JWT token: {}"), e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(("JWT token is expired: {}"), e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(("JWT token is unsupported: {}"), e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(("JWT claims string is empty: {}"), e.getMessage());
        }

        return false;
    }

}

