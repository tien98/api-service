package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "MENU")
@NoArgsConstructor
public class MenuDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENUID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "MENUNAME")
    private String menuName;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "ISACTIVE")
    @ApiModelProperty(hidden = true)
    private Integer isActive;
}