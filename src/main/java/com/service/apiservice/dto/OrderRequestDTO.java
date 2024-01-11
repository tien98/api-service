package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.apiservice.model.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
@Setter
@Getter
public class OrderRequestDTO extends BaseDTO {
    @JsonIgnore
    private Integer id;

    private String orderNumber;

    private String serviceCategoryId;

    private String serviceId;

    private Integer userId;

    private Date bookDate;

    private String bookTime;

    private String email;

    private String phone;
    private String fullName;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;

    private String address;

    private String note;

    private String comments;

    private Integer orderStatusId;

    private String reason;

    private Integer paymentMethodId;

    private Double subTotal;

    private Double total;

    private Boolean status = false;

    private String acreageType;

    private Integer dateType;

    private String orderCode;
}