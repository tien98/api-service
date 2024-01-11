package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignUpDTO {
    @NotNull
    @ApiModelProperty(position = 0)
    private String username;

    @NotNull
    @ApiModelProperty(position = 1)
    private String password;
}
