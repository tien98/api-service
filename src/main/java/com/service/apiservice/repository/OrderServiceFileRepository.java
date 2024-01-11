package com.service.apiservice.repository;

import com.service.apiservice.model.OrderServiceFileDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderServiceFileRepository extends JpaRepository<OrderServiceFileDTO, Integer> {
    List<OrderServiceFileDTO> getAllByOrderIdIsAndEventIdIs(int orderId, int eventId);
}
