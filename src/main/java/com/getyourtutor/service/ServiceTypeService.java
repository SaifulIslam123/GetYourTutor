package com.getyourtutor.service;

import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeResponse createServiceType(ServiceTypeRequest request);
    ServiceTypeResponse updateServiceType(Long serviceTypeId, ServiceTypeRequest request);
    void deleteServiceType(Long serviceTypeId);
    ServiceTypeResponse getServiceTypeById(Long serviceTypeId);
    List<ServiceTypeResponse> getAllServiceTypes();
    Page<ServiceTypeResponse> getAllServiceTypes(Pageable pageable);
}
