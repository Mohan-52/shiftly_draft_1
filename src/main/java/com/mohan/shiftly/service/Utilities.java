package com.mohan.shiftly.service;

import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utilities {
    private final UserRepository userRepository;

    public User getLoggedInUser(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).get();
    }
}
