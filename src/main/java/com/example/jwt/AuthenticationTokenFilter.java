package com.example.jwt;

import com.example.services.UserDetailsImplService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;

    private final UserDetailsImplService userDetailsImplService;

    public AuthenticationTokenFilter(JwtUtilities jwtUtilities, UserDetailsImplService userDetailsImplService) {
        this.jwtUtilities = jwtUtilities;
        this.userDetailsImplService = userDetailsImplService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = parseJwt(httpServletRequest);
            if(jwtUtilities.validateJwtToken(token)){
                String username = jwtUtilities.getUserNameFromToken(token);

                UserDetails userDetails = userDetailsImplService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        catch (Exception e){
            log.error("Cannot set user authentication. Filter Exception.");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
return null;
    }
}
