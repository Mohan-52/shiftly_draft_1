package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.TaskReqDto;
import com.mohan.shiftly.entity.Task;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.Priority;
import com.mohan.shiftly.enums.TaskStatus;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.TaskRepository;
import com.mohan.shiftly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final Utilities utilities;
    private final UserRepository userRepo;

    public GenericResDto assignTask(TaskReqDto request){
        Task task=new Task();
        User user=userRepo.findById(request.getAssignedTo())
                .orElseThrow(()-> new ResourceNotFoundEx("User not found with id "+request.getAssignedTo()));

        Priority priority;

        try{
            priority=Priority.valueOf(request.getPriority());
        }catch (Exception ex){
            throw new IllegalArgumentException("Invalid Priority");
        }

        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(priority);
        task.setAssignedTo(user);
        task.setAssignedBy(utilities.getLoggedInUser());

        task.setTaskStatus(TaskStatus.PENDING);
        task.setTitle(request.getTitle());

       Task savedTask= taskRepo.save(task);

       return new GenericResDto("Task Successfully created with id "+savedTask.getId());
    }

}
