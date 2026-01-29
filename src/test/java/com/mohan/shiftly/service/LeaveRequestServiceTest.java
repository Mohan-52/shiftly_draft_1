package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LeaveReqReqDto;
import com.mohan.shiftly.entity.LeaveRequest;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {
    @InjectMocks
    private LeaveRequestService service;

    @Mock
    private LeaveRequestRepository repository;

    @Mock
    private Utilities utilities;

    // apply leave

    @Test
    void apply_success(){
        LeaveReqReqDto req=new LeaveReqReqDto();
        req.setReason("Random Reason");
        req.setStartDate(LocalDate.of(2026,12,1));
        req.setEndDate(LocalDate.of(2026,12,5));



        User user=new User();
        User manager=new User();
        user.setManager(manager);

        LeaveRequest leave=new LeaveRequest();

        leave.setId(10L);

        Mockito.when(utilities.getLoggedInUser()).thenReturn(user);
        Mockito.when(repository.save(leave)).thenReturn(leave);


        GenericResDto res=service.applyLeave(req);

        assertEquals("Leave Successfully applied with id 10",res.getMessage());


    }



}