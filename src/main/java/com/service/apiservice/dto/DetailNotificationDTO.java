package com.service.apiservice.dto;

import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.model.OrderDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailNotificationDTO {
    private NotificationDTO notificationDTO;
    private OrderDTO orderDTO;
}
