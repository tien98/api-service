package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CodeRequestDTO {
    @NotNull
    @ApiModelProperty(position = 0)
    private String username;

    @NotNull
    @ApiModelProperty(position = 1)
    private String code;
}
