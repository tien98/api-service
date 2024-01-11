package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryPriceRequestDTO {
    @ApiModelProperty(position = 0)
    private Integer typeOption;
    @ApiModelProperty(position = 1)
    private Integer serviceCategoryId;
}
