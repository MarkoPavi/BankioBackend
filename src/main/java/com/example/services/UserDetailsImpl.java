package com.example.services;

import com.example.models.UserData;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserData user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getLevel().name()))
                .collect(Collectors.toList());

        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(authorities)
                .build();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
