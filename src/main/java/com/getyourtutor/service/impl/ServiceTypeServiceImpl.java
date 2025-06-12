package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.repository.ServiceTypeRepository;
import com.getyourtutor.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }
}
