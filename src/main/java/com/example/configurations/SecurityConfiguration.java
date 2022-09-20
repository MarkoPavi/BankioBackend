package com.example.configurations;

import com.example.jwt.AuthEntryPoint;
import com.example.jwt.AuthenticationTokenFilter;
import com.example.services.UserDetailsImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    UserDetailsImplService userService;

    @Autowired
    private AuthEntryPoint authErrorCatcher;

    @Autowired
    private AuthenticationTokenFilter authTokenFilter;

     @Bean
    public DaoAuthenticationProvider authProvider(){
         DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

         daoAuthProvider.setUserDetailsService(userService);
         daoAuthProvider.setPasswordEncoder(passwordEncoder());
         return daoAuthProvider;
     }

    /*@Bean
    public AuthenticationManager authManagerFromConfig(AuthenticationConfiguration authConfig) throws Exception{
         return authConfig.getAuthenticationManager();
     }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
         security.cors().and().csrf().disable()
                 .exceptionHandling().authenticationEntryPoint(authErrorCatcher)
                 .and()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                 .antMatchers("/api/test/**").permitAll()
                 .anyRequest().authenticated();
     security.authenticationProvider(authProvider());
     security.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

     return security.build();

     }

}
