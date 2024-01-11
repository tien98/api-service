package com.service.apiservice.service.impl;

import com.service.apiservice.dto.CategoryPriceRequestDTO;
import com.service.apiservice.dto.CategoryPriceResponseDTO;
import com.service.apiservice.model.ServiceCategoryTypeTO;
import com.service.apiservice.model.ServiceDTO;
import com.service.apiservice.model.ServiceTypePriceTO;
import com.service.apiservice.repository.ServiceCategoryTypeRepository;
import com.service.apiservice.repository.ServiceRepository;
import com.service.apiservice.repository.ServiceTypePriceRepository;
import com.service.apiservice.service.ServiceCategoryTypeService;
import com.service.apiservice.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServiceCategoryTypeServiceImpl implements ServiceCategoryTypeService {
    @Autowired
    ServiceCategoryTypeRepository serviceCategoryTypeRepository;
    @Autowired
    ServiceTypePriceRepository serviceTypePriceRepository;

    public List<ServiceCategoryTypeTO> getList() {
        return serviceCategoryTypeRepository.getAllByStatusIsTrue();
    }
    public List<CategoryPriceResponseDTO> getListCategoryPrice(CategoryPriceRequestDTO request) {
        List<ServiceCategoryTypeTO> listCategory = serviceCategoryTypeRepository.getAllByStatusIsTrueAndTypeOptionIsAndServiceCategoryIdIs(request.getTypeOption(), request.getServiceCategoryId());
        List<CategoryPriceResponseDTO> categoryPriceResponseDTO = new ArrayList<>();
        try {
            if(listCategory.isEmpty()) {
                return null;
            }

            for (var item : listCategory) {
                List<ServiceTypePriceTO> serviceTypePriceList = serviceTypePriceRepository.getAllByServiceCategoryTypeIdIs(item.getId());
                if(!serviceTypePriceList.isEmpty()) {
                    ServiceTypePriceTO serviceTypePriceTO = serviceTypePriceList.get(0);
                    CategoryPriceResponseDTO categoryPrice = new CategoryPriceResponseDTO();
                    categoryPrice.setPrice(serviceTypePriceTO.getPrice());
                    categoryPrice.setType(item.getType());
                    categoryPrice.setTypeOption(item.getTypeOption());
                    categoryPrice.setServiceCategoryId(item.getServiceCategoryId());
                    categoryPriceResponseDTO.add(categoryPrice);
                }
            }
        }catch (Exception ex) {
            return null;
        }
        return categoryPriceResponseDTO;
    }
}
