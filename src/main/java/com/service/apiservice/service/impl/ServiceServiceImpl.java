package com.service.apiservice.service.impl;

import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.model.ServiceDTO;
import com.service.apiservice.repository.ServiceCategoryRepository;
import com.service.apiservice.repository.ServiceRepository;
import com.service.apiservice.service.ServiceCategoryService;
import com.service.apiservice.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    public List<ServiceDTO> getList() {
        return serviceRepository.getAllByStatusIsTrue();
    }

    public List<ServiceDTO> getListByServiceCategoryId(Integer serviceCategoryId){
        return  serviceRepository.findAllByServiceCategoryId(serviceCategoryId);
    }
}
