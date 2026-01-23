package com.mohan.shiftly.dto;

import com.mohan.shiftly.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LeaveReqResDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String appliedBy;
}
