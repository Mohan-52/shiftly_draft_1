package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LeaveReqReqDto;
import com.mohan.shiftly.dto.LeaveReqResDto;
import com.mohan.shiftly.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LeaveRequestController {

    private final LeaveRequestService service;

    @PostMapping("/apply-leave")
    public ResponseEntity<GenericResDto>  applyLeave(@RequestBody LeaveReqReqDto reqDto){
       return  ResponseEntity.status(HttpStatus.CREATED).body(service.applyLeave(reqDto));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/reject-leave/{id}")
    public ResponseEntity<GenericResDto> rejectLeave(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.rejectReqLeave(id));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/approve-leave/{id}")
    public ResponseEntity<GenericResDto> approveLeave(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.leaveApproveReqLeave(id));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/leaves/applied-to-me")
    public List<LeaveReqResDto> getLeavesAppliedToMe(){
        return  service.getLeavesReq();
    }
}
