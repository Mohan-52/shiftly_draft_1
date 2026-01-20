package com.mohan.shiftly.security;

import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundEx("User not found with email "+email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_"+user.getRole().name())
                .build();
    }
}
