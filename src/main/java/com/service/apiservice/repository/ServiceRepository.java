package com.service.apiservice.repository;

import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.model.ServiceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceDTO, Integer> {
    List<ServiceDTO> getAllByStatusIsTrue();
    List<ServiceDTO> findAllByServiceCategoryId(Integer serviceCategoryId);
}
