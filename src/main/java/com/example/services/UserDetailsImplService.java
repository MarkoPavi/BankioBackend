package com.example.services;

import com.example.interfaces.UserRepository;
import com.example.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserData userData = userRepository.findUserDataByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "+ username));

        return UserDetailsImpl.build(userData);
    }


}
