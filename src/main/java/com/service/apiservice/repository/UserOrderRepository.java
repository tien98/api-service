package com.service.apiservice.repository;

import com.service.apiservice.model.UserOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrderDTO, Integer> {
}
