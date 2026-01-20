package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.entity.Department;
import com.mohan.shiftly.exception.ResourceAlreadyExistsEx;
import com.mohan.shiftly.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Department> getDepartments(int page, int size){
        Pageable pageable= PageRequest.of(page,size);

        return departmentRepository.findAll(pageable).getContent();
    }

}
