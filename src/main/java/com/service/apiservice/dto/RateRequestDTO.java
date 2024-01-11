package com.service.apiservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RateRequestDTO {
    private Integer orderId;
    private Integer rate;
    private String content;
}