package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<GenericResDto> createDepartment(Department department){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(department));
    }
}
