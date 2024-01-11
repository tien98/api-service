package com.service.apiservice.dto;
import java.util.List;

import com.service.apiservice.model.AppUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {
    @ApiModelProperty(position = 0)
    private String username;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private String password;
    @ApiModelProperty(position = 3)
    private String fullName;
    @ApiModelProperty(position = 4)
    private String phone;
    @ApiModelProperty(position = 5)
    private String code;
    @ApiModelProperty(position = 6)
    List<AppUserRole> appUserRoles;
}
