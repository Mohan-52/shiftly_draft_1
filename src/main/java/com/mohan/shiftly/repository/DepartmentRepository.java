package com.mohan.shiftly.repository;

import com.mohan.shiftly.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByDepartmentName(String department);
    Optional<Department> findByDepartmentName(String name);
}
