package com.service.apiservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FCMRequestDTO {
    private FCMNotificationDTO notification;
    private String to;
}
