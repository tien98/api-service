package com.service.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMDataDTO {
    private String priority;
    private String sound;
    private Boolean contentAvailable;
    private String bodyText;
    private String organization;
}
