package com.mohan.shiftly.controller;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/punch")
    public ResponseEntity<GenericResDto> clockInOrOut(){
        GenericResDto res = attendanceService.clockInAndClockOut();

        if (res.getMessage().contains("Clocked in")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } else {
            return ResponseEntity.ok(res);
        }
    }
}
