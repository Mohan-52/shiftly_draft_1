package com.mohan.shiftly.bootstrap;

import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.Roles;
import com.mohan.shiftly.enums.Status;
import com.mohan.shiftly.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootStrap {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createAdmin(){
        if(!userRepository.existsByEmail(adminEmail)){
            User user=new User();
            user.setEmail(adminEmail);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setStatus(Status.ACTIVE);
            user.setFirstName("System");
            user.setLastName("Admin");
            user.setRole(Roles.ADMIN);

            userRepository.save(user);
        }
    }
}
