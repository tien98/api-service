package com.service.apiservice.repository;

import com.service.apiservice.model.CustomerDeviceTokenDTO;
import com.service.apiservice.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CustomerDeviceTokenRepository extends JpaRepository<CustomerDeviceTokenDTO, Integer> {
    List<CustomerDeviceTokenDTO> getAllByImeiEquals(String imei);
    CustomerDeviceTokenDTO findAllByImeiEqualsAndUserIdIs(String imei, Integer userId);
    List<CustomerDeviceTokenDTO> getAllByUserIdIsAndDeviceTokenNotNull(Integer userId);
}
