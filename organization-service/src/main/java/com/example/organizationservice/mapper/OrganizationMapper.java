package com.example.organizationservice.mapper;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import org.modelmapper.ModelMapper;

public class OrganizationMapper {

    public static OrganizationDto toOrganizationDto(Organization organization, ModelMapper modelMapper) {
        return modelMapper.map(organization, OrganizationDto.class);
    }

    public static Organization toOrganization(OrganizationDto organizationDto, ModelMapper modelMapper) {
        return modelMapper.map(organizationDto, Organization.class);
    }
}
