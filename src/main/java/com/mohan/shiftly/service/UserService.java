package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.UserRegisterReqDto;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.exception.ResourceAlreadyExistsEx;
import com.mohan.shiftly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public GenericResDto registerUser(UserRegisterReqDto reqDto){
        if(userRepository.existsByEmail(reqDto.getEmail())){
            throw new ResourceAlreadyExistsEx("Employee with email "+reqDto.getEmail()+" already exist");
        }

        User user=new User();
       // user.setRole();

        userRepository.save(user);

        return null;

    }

}
