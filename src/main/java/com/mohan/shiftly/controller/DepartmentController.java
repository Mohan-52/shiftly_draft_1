package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<GenericResDto> createDepartment(@RequestBody Department department){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(department));
    }

    @GetMapping
    public List<Department> getAllDepartments(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size){
        return departmentService.getDepartments(page,size);
    }
}
