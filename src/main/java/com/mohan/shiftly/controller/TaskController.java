package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.TaskReqDto;
import com.mohan.shiftly.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService service;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/assign")
    public ResponseEntity<GenericResDto> assignTask(@RequestBody TaskReqDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.assignTask(request));
    }
}
