package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class UpdateStatusRequestDTO {
    private Integer orderId;
    private Integer statusId;
    private String reason;
}