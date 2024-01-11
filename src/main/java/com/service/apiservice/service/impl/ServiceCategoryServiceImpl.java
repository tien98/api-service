package com.service.apiservice.service.impl;

import com.service.apiservice.model.DistrictDTO;
import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.model.WardDTO;
import com.service.apiservice.repository.MenuRepository;
import com.service.apiservice.repository.ServiceCategoryRepository;
import com.service.apiservice.service.MenuService;
import com.service.apiservice.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategoryDTO> getList() {
        return serviceCategoryRepository.getAllByStatusIsTrue();
    }

    public ServiceCategoryDTO getOne(Integer serviceCategoryId) {
        ServiceCategoryDTO res = serviceCategoryRepository.findById(serviceCategoryId).orElse(null);
        if(res == null) {
            return null;
        }
        ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();

        serviceCategoryDTO.setId(res.getId());
        serviceCategoryDTO.setName(res.getName());
        serviceCategoryDTO.setDescription(res.getDescription());
        serviceCategoryDTO.setIconApp(res.getIconApp());
        serviceCategoryDTO.setIconWeb(res.getIconWeb());
        serviceCategoryDTO.setPathScreen(res.getPathScreen());
        serviceCategoryDTO.setPrice(res.getPrice());
        serviceCategoryDTO.setStatus(res.getStatus());
        serviceCategoryDTO.setCreatedAt(res.getCreatedAt());
        serviceCategoryDTO.setCreatedBy(res.getCreatedBy());
        serviceCategoryDTO.setLastUpdatedAt(res.getLastUpdatedAt());
        serviceCategoryDTO.setLastUpdatedBy(res.getLastUpdatedBy());
        serviceCategoryDTO.setCode(res.getCode());
        serviceCategoryDTO.setPricePromotion(res.getPricePromotion());
        return serviceCategoryDTO;
    }
}
