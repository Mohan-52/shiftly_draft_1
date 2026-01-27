package com.mohan.shiftly.dto;

import com.mohan.shiftly.enums.Priority;
import com.mohan.shiftly.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResDto {
    private Long id;
    private String title;
    private String description;
    private String assignedBy;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

}
