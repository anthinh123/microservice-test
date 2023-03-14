package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.ApiClient;
import com.example.employeeservice.service.EmployeeService;
import com.example.employeeservice.service.OrganizationClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private ModelMapper modelMapper;

    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

//    private WebClient webClient;

    private ApiClient apiClient;
    private OrganizationClient organizationClient;

    @Override
    public EmployeeDto saveEmployeeDto(EmployeeDto employeeDto) {
        Employee saveEmployee = employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
        EmployeeDto saveEmployeeDto = modelMapper.map(saveEmployee, EmployeeDto.class);
        return saveEmployeeDto;
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeesDto = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
        return employeesDto;
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public ApiResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("employee", "id", id)
        );
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8181/api/department/" + employee.getDepartmentCode(),
//                DepartmentDto.class);
        //        DepartmentDto departmentDto = responseEntity.getBody();

//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8181/api/department/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();
        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

//        OrganizationDto organizationDto = organizationClient.getOrganizationByCode(employee.getOrganizationCode());

        ResponseEntity<OrganizationDto> responseEntity = restTemplate.getForEntity("http://localhost:8383/api/organization/" + employee.getOrganizationCode(),
                OrganizationDto.class);
        OrganizationDto organizationDto = responseEntity.getBody();

        return new ApiResponseDto(modelMapper.map(employee, EmployeeDto.class), departmentDto, organizationDto);
    }

    public ApiResponseDto getDefaultDepartment(Long id, Exception exception) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("employee", "id", id)
        );

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D");
        departmentDto.setDepartmentDescription("Research and development ");
        departmentDto.setDepartmentCode("R1");

        OrganizationDto organizationDto =  new OrganizationDto();
        organizationDto.setOrganizationName("Unknown");
        organizationDto.setOrganizationDescription("Unknown");
        organizationDto.setOrganizationCode("Unknown");

        return new ApiResponseDto(modelMapper.map(employee, EmployeeDto.class), departmentDto, organizationDto);
    }
}
