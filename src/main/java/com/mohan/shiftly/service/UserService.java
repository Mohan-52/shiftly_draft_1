package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LoginReqDto;
import com.mohan.shiftly.dto.LoginResDto;
import com.mohan.shiftly.dto.UserRegisterReqDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.Roles;
import com.mohan.shiftly.enums.Status;
import com.mohan.shiftly.exception.InvalidCrenentialEx;
import com.mohan.shiftly.exception.ResourceAlreadyExistsEx;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.DepartmentRepository;
import com.mohan.shiftly.repository.UserRepository;
import com.mohan.shiftly.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepo;
    private final JwtUtil jwtUtil;

    private User getLoggedInUser(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).get();
    }

    public GenericResDto registerUser(UserRegisterReqDto reqDto){
        if(userRepository.existsByEmail(reqDto.getEmail())){
            throw new ResourceAlreadyExistsEx("Employee with email "+reqDto.getEmail()+" already exist");
        }



        User user=new User();
        Roles role;

        try{
            role= Roles.valueOf(reqDto.getRole().toUpperCase());
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid role");
        }

        Department department=departmentRepo.findById(reqDto.getDepartmentId())
                        .orElseThrow(()->new ResourceNotFoundEx("Department with id "+reqDto.getDepartmentId()+" doesn't exists"));

        user.setRole(role);
        user.setStatus(Status.ACTIVE);
        user.setFirstName(reqDto.getFirstName());
        user.setLastName(reqDto.getLastName());
        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));
        user.setEmail(reqDto.getEmail());
        user.setDepartment(department);
        user.setManager(getLoggedInUser());

        User savedUser= userRepository.save(user);

        return new GenericResDto("User Successfully created with id "+savedUser.getId()+" and role of "+savedUser.getRole().name());

    }

    public LoginResDto login(LoginReqDto reqDto){
        User user=userRepository.findByEmail(reqDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundEx("User with email "+reqDto.getEmail()+" does not exists"));

        if(!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())){
            throw new InvalidCrenentialEx("Incorrect password");
        }

        return new LoginResDto(jwtUtil.generateJwtToken(user.getEmail()),user.getRole().name());
    }

}
