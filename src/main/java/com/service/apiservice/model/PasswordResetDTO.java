package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "PASSWORD_RESETS")
@NoArgsConstructor
public class PasswordResetDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "userName")
    private String userName;

    @Column(name = "status")
    private Boolean status = true;
}