package com.service.apiservice.repository;

import com.service.apiservice.model.ProvinceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceDTO, Integer> {
    List<ProvinceDTO> findAllByStatusIs(Integer status);
}
