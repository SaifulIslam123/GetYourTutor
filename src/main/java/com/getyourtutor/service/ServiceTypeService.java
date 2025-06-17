package com.getyourtutor.service;

import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeResponse createServiceType(ServiceTypeRequest request);

    ServiceTypeResponse getServiceTypeById(Long id);

    Page<ServiceTypeResponse> getAllServiceTypes(Pageable pageable);

    ServiceTypeResponse updateServiceType(Long id, ServiceTypeRequest request);

    void deleteServiceType(Long id);
}
