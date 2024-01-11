package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
public class OrderSearchResponseDTO {
    private Integer id;

    private String orderNumber;

    private String phone;

    private Integer orderStatusId;

    private Double subTotal;

    private Double total;

    private String fullName;

    private Timestamp createdAt;
}