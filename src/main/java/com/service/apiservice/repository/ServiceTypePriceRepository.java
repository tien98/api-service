package com.service.apiservice.repository;

import com.service.apiservice.model.ServiceCategoryTypeTO;
import com.service.apiservice.model.ServiceTypePriceTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypePriceRepository extends JpaRepository<ServiceTypePriceTO, Integer> {
    List<ServiceTypePriceTO> getAllByServiceCategoryTypeIdIs(Integer categoryTypeId);
    List<ServiceTypePriceTO> getAllByServiceTypeIdIs(Integer serviceTypeId);
}
