package com.service.apiservice.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.service.apiservice.model.AppUserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
public class UserResponseDTO {
    @ApiModelProperty(position = 0)
    private Integer id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;
    @ApiModelProperty(position = 3)
    private String fullName;
    @ApiModelProperty(position = 4)
    private Timestamp emailVerifiedAt;
    @ApiModelProperty(position = 5)
    private String phone;
    @ApiModelProperty(position = 6)
    private String code;
    @ApiModelProperty(position = 8)
    private String avatar;
    @ApiModelProperty(position = 9)
    private Date dob;
    @ApiModelProperty(position = 10)
    private Integer provinceId;
    @ApiModelProperty(position = 11)
    private Integer districtId;
    @ApiModelProperty(position = 12)
    private Integer wardId;
    @ApiModelProperty(position = 13)
    private String address;
    @ApiModelProperty(position = 14)
    List<AppUserRole> appUserRoles;
    @ApiModelProperty(position = 15)
    private Integer sex;
}
