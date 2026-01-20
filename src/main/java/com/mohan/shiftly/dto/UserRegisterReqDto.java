package com.mohan.shiftly.dto;

import lombok.Data;

@Data
public class UserRegisterReqDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Long departmentId;
}
