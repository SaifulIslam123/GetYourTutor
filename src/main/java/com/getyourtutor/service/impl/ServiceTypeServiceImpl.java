package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import com.getyourtutor.repository.ServiceTypeRepository;
import com.getyourtutor.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    // --- DTO methods ---
    @Override
    public ServiceTypeResponse createServiceType(ServiceTypeRequest request) {
        // TODO real implementation
        return ServiceTypeResponse.builder()
                .serviceTypeId(0L)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    @Override
    public ServiceTypeResponse getServiceTypeById(Long id) {
        // TODO
        return ServiceTypeResponse.builder().serviceTypeId(id).name("Test").build();
    }

    @Override
    public Page<ServiceTypeResponse> getAllServiceTypes(Pageable pageable) {
        return new PageImpl<>(List.of());
    }

    @Override
    public ServiceTypeResponse updateServiceType(Long id, ServiceTypeRequest request) {
        return ServiceTypeResponse.builder().serviceTypeId(id).name(request.getName()).description(request.getDescription()).build();
    }

    @Override
    public void deleteServiceType(Long id) {
        // TODO
    }
}
