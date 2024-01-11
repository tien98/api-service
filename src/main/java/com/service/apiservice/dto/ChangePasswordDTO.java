package com.service.apiservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class ChangePasswordDTO {
    @NotNull
    @ApiModelProperty(position = 1)
    private String oldPassword;

    @NotNull
    @ApiModelProperty(position = 2)
    private String newPassword;
}

