package com.service.apiservice.repository;

import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.NotificationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationCustomRepository {
    Boolean saveNotify(NotificationDTO notificationDTO, List<AppUser> appUserList);
    Boolean sendNotify(NotificationDTO notificationDTO, Integer userId);
}
