package com.mohan.shiftly.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveReqReqDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
