package com.mohan.shiftly.repository;

import com.mohan.shiftly.entity.Task;
import com.mohan.shiftly.enums.TaskStatus;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findByAssignedTo_IdAndTaskStatusNot(Long id, TaskStatus taskStatus, Pageable pageable);
}
