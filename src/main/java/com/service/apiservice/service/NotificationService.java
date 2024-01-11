package com.service.apiservice.service;

import com.service.apiservice.dto.DetailNotificationDTO;
import com.service.apiservice.model.NotificationDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface NotificationService {
    Map<String, Object> getAllNotification(Pageable pageable, HttpServletRequest req);
    Integer getNotificationsUnRead(int userId, HttpServletRequest req);
    Boolean updateReadNotify(int notificationId, boolean isRead);
    DetailNotificationDTO getDetailNotification(int notificationId);
}
