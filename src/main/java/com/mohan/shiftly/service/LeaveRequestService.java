package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.LeaveReqReqDto;
import com.mohan.shiftly.dto.LeaveReqResDto;
import com.mohan.shiftly.entity.LeaveRequest;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.LeaveStatus;
import com.mohan.shiftly.exception.ClockOutEx;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepo;
    private final Utilities utilities;

    public LeaveReqResDto mapToDto(LeaveRequest leaveRequest){
        LeaveReqResDto leaveReqResDto=new LeaveReqResDto();
        leaveReqResDto.setId(leaveRequest.getId());
        leaveReqResDto.setAppliedBy(leaveRequest.getUser().getFirstName()+" "+leaveRequest.getUser().getLastName());
        leaveReqResDto.setStartDate(leaveRequest.getStartDate());
        leaveReqResDto.setEndDate(leaveRequest.getEndDate());
        leaveReqResDto.setReason(leaveRequest.getReason());

        return leaveReqResDto;
    }

    public GenericResDto applyLeave(LeaveReqReqDto reqDto){

        User user=utilities.getLoggedInUser();
        LeaveRequest request=new LeaveRequest();
        request.setReason(reqDto.getReason());
        request.setStartDate(reqDto.getStartDate());
        request.setEndDate(reqDto.getEndDate());
        request.setStatus(LeaveStatus.PENDING);
        request.setUser(user);
        request.setManager(user.getManager());
        LeaveRequest savedRequest= leaveRequestRepo.save(request);

        return new GenericResDto("Leave Successfully applied with id"+savedRequest.getId());

    }

    public GenericResDto rejectReqLeave(Long id){
        LeaveRequest leaveRequest=leaveRequestRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Leave request not found with id "+id));

       if(!leaveRequest.getManager().getId().equals(utilities.getLoggedInUser().getId())){
           throw new ClockOutEx("You are not the manager for this employee");
       }

        leaveRequest.setStatus(LeaveStatus.REJECTED);

        leaveRequestRepo.save(leaveRequest);
        return new GenericResDto("Leave Request Rejected");

    }

    public GenericResDto leaveApproveReqLeave(Long id){
        LeaveRequest leaveRequest=leaveRequestRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Leave request not found with id "+id));

        if(!leaveRequest.getManager().getId().equals(utilities.getLoggedInUser().getId())){
            throw new ClockOutEx("You are not the manager for this employee");
        }

        leaveRequest.setStatus(LeaveStatus.APPROVED);

        leaveRequestRepo.save(leaveRequest);

        return new GenericResDto("Leave Request Approved");

    }

     public List<LeaveReqResDto> getLeavesReq(){
        return leaveRequestRepo.findByManager_IdAndStatus(utilities.getLoggedInUser().getId(),LeaveStatus.PENDING)
                .stream()
                .map(this::mapToDto)
                .toList();

     }


}
