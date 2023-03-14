package com.example.employeeservice.service;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto saveEmployeeDto(EmployeeDto employeeDto);

    List<EmployeeDto> getEmployees();

    ApiResponseDto getEmployeeById(Long id);
}
