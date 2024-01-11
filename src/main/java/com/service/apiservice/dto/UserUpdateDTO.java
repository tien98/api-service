package com.service.apiservice.dto;

import com.service.apiservice.model.AppUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class UserUpdateDTO {
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private Integer sex;
    @ApiModelProperty(position = 3)
    private Date dob;
    @ApiModelProperty(position = 4)
    private String phone;
    @ApiModelProperty(position = 5)
    private Integer provinceId;
    @ApiModelProperty(position = 6)
    private Integer districtId;
    @ApiModelProperty(position = 7)
    private Integer wardId;
    @ApiModelProperty(position = 8)
    private String address;
    @ApiModelProperty(position = 9)
    private String fullName;
}

