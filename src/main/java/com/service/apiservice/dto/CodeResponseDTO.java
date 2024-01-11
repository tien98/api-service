package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CodeResponseDTO {
    private String username;
    private String mail;
    private Boolean isSuccess;
    private String message;
    private String code;
}
