package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.ServiceTypeRepository;
import com.getyourtutor.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    @Transactional
    public ServiceTypeResponse createServiceType(ServiceTypeRequest request) {
        if (serviceTypeRepository.existsByName(request.getName())) {
            throw new BadRequestException("Service type with name '" + request.getName() + "' already exists.");
        }

        ServiceType serviceType = new ServiceType();
        serviceType.setName(request.getName());
        serviceType.setDescription(request.getDescription());
        serviceType.setIsActive(true);

        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);
        return mapToServiceTypeResponse(savedServiceType);
    }

    @Override
    @Transactional
    public ServiceTypeResponse updateServiceType(Long serviceTypeId, ServiceTypeRequest request) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + serviceTypeId));

        serviceTypeRepository.findByName(request.getName()).ifPresent(st -> {
            if (!st.getServiceTypeId().equals(serviceTypeId)) {
                throw new BadRequestException("Service type with name '" + request.getName() + "' already exists.");
            }
        });

        serviceType.setName(request.getName());
        serviceType.setDescription(request.getDescription());

        ServiceType updatedServiceType = serviceTypeRepository.save(serviceType);
        return mapToServiceTypeResponse(updatedServiceType);
    }

    @Override
    @Transactional
    public void deleteServiceType(Long serviceTypeId) {
        if (!serviceTypeRepository.existsById(serviceTypeId)) {
            throw new ResourceNotFoundException("Service type not found with id: " + serviceTypeId);
        }
        serviceTypeRepository.deleteById(serviceTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeResponse getServiceTypeById(Long serviceTypeId) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + serviceTypeId));
        return mapToServiceTypeResponse(serviceType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceTypeResponse> getAllServiceTypes() {
        return serviceTypeRepository.findAll().stream()
                .map(this::mapToServiceTypeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceTypeResponse> getAllServiceTypes(Pageable pageable) {
        return serviceTypeRepository.findAll(pageable).map(this::mapToServiceTypeResponse);
    }

    private ServiceTypeResponse mapToServiceTypeResponse(ServiceType serviceType) {
        return ServiceTypeResponse.builder()
                .serviceTypeId(serviceType.getServiceTypeId())
                .name(serviceType.getName())
                .description(serviceType.getDescription())
                .build();
    }
}
