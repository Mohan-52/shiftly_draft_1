package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LoginReqDto;
import com.mohan.shiftly.dto.LoginResDto;
import com.mohan.shiftly.dto.UserRegisterReqDto;
import com.mohan.shiftly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto>  login(@RequestBody LoginReqDto reqDto){
        return ResponseEntity.ok(userService.login(reqDto));
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResDto> register(@RequestBody UserRegisterReqDto reqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(reqDto));
    }
}
