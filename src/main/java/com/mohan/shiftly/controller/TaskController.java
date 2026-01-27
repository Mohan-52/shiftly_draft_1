package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.TaskReqDto;
import com.mohan.shiftly.dto.TaskResDto;
import com.mohan.shiftly.entity.Task;
import com.mohan.shiftly.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/assign")
    public ResponseEntity<GenericResDto> assignTask(@RequestBody TaskReqDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.assignTask(request));
    }

    @GetMapping
    public List<TaskResDto> getMyPendingTask(){
        return service.getMyPendingTask();
    }

    @PutMapping("/{id}")
    public GenericResDto updateTaskStatus(@PathVariable Long id, @RequestParam String status){
        return service.updateTask(id,status);
    }
}
