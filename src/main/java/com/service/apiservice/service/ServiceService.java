package com.service.apiservice.service;

import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.model.ServiceDTO;

import java.util.List;

public interface ServiceService {
    List<ServiceDTO> getList();
    List<ServiceDTO> getListByServiceCategoryId(Integer serviceCategoryId);
}
