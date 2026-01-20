package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.exception.ResourceAlreadyExistsEx;
import com.mohan.shiftly.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public GenericResDto createDepartment(Department department){
        if(departmentRepository.existsByDepartmentName(department.getDepartmentName())){
            throw new ResourceAlreadyExistsEx("Department with name "+department.getDepartmentName()+" already exists");
        }

        Department savedDepartment= departmentRepository.save(department);

        return new GenericResDto("Department successfully created with id "+savedDepartment.getId());
    }

}
