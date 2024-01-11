package com.service.apiservice.repository;

import com.service.apiservice.model.ServiceCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepository  extends JpaRepository<ServiceCategoryDTO, Integer> {
    Optional<ServiceCategoryDTO> findByName(String name);
    Optional<ServiceCategoryDTO> findById(Integer serviceCategoryId);
    List<ServiceCategoryDTO> getAllByStatusIsTrue();
}
