package com.mohan.shiftly.repository;

import com.mohan.shiftly.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
