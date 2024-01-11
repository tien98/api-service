package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.apiservice.model.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class UserOrderRequestDTO extends BaseDTO {
    @JsonIgnore
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private Integer orderStatusId;
}