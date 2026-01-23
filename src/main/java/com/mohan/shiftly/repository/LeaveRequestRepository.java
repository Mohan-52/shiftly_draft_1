package com.mohan.shiftly.repository;

import com.mohan.shiftly.entity.LeaveRequest;
import com.mohan.shiftly.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByManager_IdAndStatus(Long id, LeaveStatus status);
}
