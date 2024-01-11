package com.service.apiservice.service;

import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.model.ServiceCategoryDTO;

import java.util.List;

public interface ServiceCategoryService {
    List<ServiceCategoryDTO> getList();
    ServiceCategoryDTO getOne(Integer serviceCategoryId);
}
