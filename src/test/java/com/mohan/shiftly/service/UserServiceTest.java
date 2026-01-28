package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LoginReqDto;
import com.mohan.shiftly.dto.LoginResDto;
import com.mohan.shiftly.dto.UserRegisterReqDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.Roles;
import com.mohan.shiftly.exception.InvalidCrenentialEx;
import com.mohan.shiftly.exception.ResourceAlreadyExistsEx;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.DepartmentRepository;
import com.mohan.shiftly.repository.UserRepository;
import com.mohan.shiftly.security.JwtUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Utilities utilities;

    @Mock
    private JwtUtil jwtUtil;

    @AfterAll
    static void allTestDone(){
        System.out.println("Successfully All Test Cases Passed");
    }


    // User Registration Test Cases

    @Test
    void registerUser_success(){
        UserRegisterReqDto req=new UserRegisterReqDto();
        req.setEmail("test@gmail.com");
        req.setRole("MANAGER");
        req.setPassword("pass123");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setDepartmentId(1L);

        Mockito.when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);

        Department department=new Department();
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        User loggedIn=new User();
        Mockito.when(utilities.getLoggedInUser()).thenReturn(loggedIn);

        Mockito.when(passwordEncoder.encode("pass123")).thenReturn("encode123");

        User saved=new User();
        saved.setId(10L);
        saved.setRole(Roles.MANAGER);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(saved);

        GenericResDto response= userService.registerUser(req);

        assertEquals("User Successfully created with id 10 and role of MANAGER", response.getMessage());

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(departmentRepository).findById(1L);

    }

    @Test
    void registerUser_emailAlreadyExists(){
        UserRegisterReqDto req=new UserRegisterReqDto();
        req.setEmail("test@gmail.com");

        Mockito.when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsEx.class, ()->userService.registerUser(req));
    }

    @Test
    void registerUser_invalidRole(){
        UserRegisterReqDto req=new UserRegisterReqDto();
        req.setEmail("test@gmail.com");
        req.setRole("INVALID_ROLE");

        Mockito.when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,()->userService.registerUser(req));
    }

    @Test
    void registerUser_departmentNotFound(){
        UserRegisterReqDto req=new UserRegisterReqDto();
        req.setEmail("test@gmail.com");
        req.setRole("EMPLOYEE");
        req.setDepartmentId(1L);

        Mockito.when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundEx.class,()->userService.registerUser(req));



    }

    // Login Test Cases

    @Test
    void login_success(){
        LoginReqDto req=new LoginReqDto();
        req.setEmail("user@gmail.com");
        req.setPassword("pass123");

        User user=new User();
        user.setEmail("user@gmail.com");
        user.setPassword("encode123");
        user.setRole(Roles.ADMIN);

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("pass123","encode123")).thenReturn(true);
        Mockito.when(jwtUtil.generateJwtToken("user@gmail.com")).thenReturn("jwt-token-123");

        LoginResDto res=userService.login(req);

        assertEquals("jwt-token-123",res.getJwtToken());
        assertEquals("ADMIN",res.getRole());
    }

    @Test
    void login_userNotFound(){
        LoginReqDto req=new LoginReqDto();
        req.setEmail("user@gmail.com");

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundEx.class,()-> userService.login(req));

    }

    @Test
    void login_invalidPassword(){
        LoginReqDto req=new LoginReqDto();
        req.setEmail("user@gmail.com");
        req.setPassword("pass123");

        User user=new User();
        user.setPassword("encode123");

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("pass123","encode123")).thenReturn(false);

        assertThrows(InvalidCrenentialEx.class,()-> userService.login(req));
    }

}