package com.example.organizationservice.service.impl;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.mapper.OrganizationMapper;
import com.example.organizationservice.repository.OrganizationRepository;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    private ModelMapper modelMapper;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization saveOrganization = organizationRepository.save(OrganizationMapper.toOrganization(organizationDto, modelMapper));
        return OrganizationMapper.toOrganizationDto(saveOrganization, modelMapper);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization savedOrganization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.toOrganizationDto(savedOrganization, modelMapper);
    }
}
