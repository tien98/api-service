package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "CUSTOMER_DEVICE_TOKENS")
@NoArgsConstructor
public class CustomerDeviceTokenDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "device_type")
    private Integer deviceType;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "imei")
    private String imei;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}