package com.example.departmentservice.repository.impl;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = modelMapper.map(departmentDto, Department.class);
        DepartmentDto saveDepartmentDto = modelMapper.map(departmentRepository.save(department), DepartmentDto.class);
        return saveDepartmentDto;
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentsDto = departments
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
        return departmentsDto;
    }

    @Override
    public DepartmentDto getDepartment(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
        return departmentDto;
    }
}
