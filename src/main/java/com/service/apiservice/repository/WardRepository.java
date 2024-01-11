package com.service.apiservice.repository;

import com.service.apiservice.model.WardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<WardDTO, Integer> {
    List<WardDTO> findAllByDistrictId(Integer districtId);
}
