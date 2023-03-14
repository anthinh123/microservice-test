package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getDepartments();

    DepartmentDto getDepartment(String departmentCode);

}
