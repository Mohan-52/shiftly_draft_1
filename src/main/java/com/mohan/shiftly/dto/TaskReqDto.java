package com.mohan.shiftly.dto;

import com.mohan.shiftly.enums.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskReqDto {
    private String title;
    private String description;
    private Long assignedTo;
    private String priority;
    private LocalDate dueDate;
}
