package com.service.apiservice.repository;

import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.model.UserNotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotificationDTO, Integer> {
    List<UserNotificationDTO> getAllByCustomerId(int userId);
    List<UserNotificationDTO> getAllByCustomerIdAndNotifyId(int userId, int notifyId);
    Page<UserNotificationDTO> findAllByCustomerId(Pageable pageable, int userId);
}
