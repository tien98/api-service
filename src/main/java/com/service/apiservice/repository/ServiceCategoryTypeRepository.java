package com.service.apiservice.repository;

import com.service.apiservice.model.ServiceCategoryTypeTO;
import com.service.apiservice.model.ServiceDTO;
import com.service.apiservice.service.ServiceCategoryTypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCategoryTypeRepository extends JpaRepository<ServiceCategoryTypeTO, Integer> {
    List<ServiceCategoryTypeTO> getAllByStatusIsTrue();

    List<ServiceCategoryTypeTO> getAllByStatusIsTrueAndTypeOptionIsAndTypeIsAndServiceCategoryIdIs(Integer typeOption, Integer type, Integer serviceCategoryId);
    List<ServiceCategoryTypeTO> getAllByStatusIsTrueAndTypeOptionIsAndServiceCategoryIdIs(Integer typeOption, Integer serviceCategoryId);
}
