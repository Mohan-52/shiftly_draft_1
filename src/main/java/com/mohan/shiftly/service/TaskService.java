package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.dto.TaskReqDto;
import com.mohan.shiftly.dto.TaskResDto;
import com.mohan.shiftly.entity.Task;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.enums.Priority;
import com.mohan.shiftly.enums.TaskStatus;
import com.mohan.shiftly.exception.ResourceNotFoundEx;
import com.mohan.shiftly.repository.TaskRepository;
import com.mohan.shiftly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final Utilities utilities;
    private final UserRepository userRepo;


    private TaskResDto mapToDto(Task task){
        TaskResDto response=new TaskResDto();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setCreatedAt(task.getCreatedAt());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setAssignedBy(task.getAssignedBy().getFirstName());
        response.setStatus(task.getTaskStatus());
        response.setDueDate(task.getDueDate());

        return response;
    }

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

    public List<TaskResDto> getMyPendingTask(int page, int size){

       Sort s=Sort.by(Sort.Order.asc("dueDate"));
        Pageable pageable= PageRequest.of(page, size, s);
        return taskRepo.findByAssignedTo_IdAndTaskStatusNot(utilities.getLoggedInUser().getId(), TaskStatus.COMPLETED, pageable)
                .getContent()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public GenericResDto updateTask(Long id, String status){
        TaskStatus taskStatus;

        try {
            taskStatus=TaskStatus.valueOf(status);
        }catch (Exception ex){
            throw new IllegalArgumentException("In valid Task Status");

        }

        Task task=taskRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundEx("Task Not found with id "+id));

        task.setTaskStatus(taskStatus);

        Task updatedTask=taskRepo.save(task);

        return new GenericResDto("Successfully updated task with id "+id+" status of "+updatedTask.getTaskStatus().name());

    }

}
