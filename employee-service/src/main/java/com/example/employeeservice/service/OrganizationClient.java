package com.example.employeeservice.service;

import com.example.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "organization-service")
public interface OrganizationClient {
    @GetMapping("api/organization/{organization-code}")
    public OrganizationDto getOrganizationByCode(@PathVariable("organization-code") String organizationCode);
}
