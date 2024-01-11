package com.service.apiservice.service;

import com.service.apiservice.dto.CategoryPriceRequestDTO;
import com.service.apiservice.dto.CategoryPriceResponseDTO;
import com.service.apiservice.model.OrderDTO;
import com.service.apiservice.model.ServiceCategoryTypeTO;

import java.util.List;

public interface ServiceCategoryTypeService {
    List<ServiceCategoryTypeTO> getList();
    List<CategoryPriceResponseDTO> getListCategoryPrice(CategoryPriceRequestDTO request);
}
