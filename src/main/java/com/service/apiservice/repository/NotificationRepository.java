package com.service.apiservice.repository;

import com.service.apiservice.model.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationDTO, Integer> {
    Page<NotificationDTO> findAllByFlagOrderEquals(Pageable pageable, int flagOrder);
    List<NotificationDTO> findByOrderIdAndFlagOrder(int orderId, int flagOrder);
    List<NotificationDTO> findAllByIsReadIsFalse();
    NotificationDTO findById(int notificationId);
}
