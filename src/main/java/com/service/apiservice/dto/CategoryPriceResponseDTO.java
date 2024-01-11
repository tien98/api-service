package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryPriceResponseDTO {
    private Integer type;
    private Integer typeOption;
    private Double price;
    private Integer serviceCategoryId;
}
