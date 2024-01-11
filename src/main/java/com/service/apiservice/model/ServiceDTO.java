package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "SERVICES")
@NoArgsConstructor
public class ServiceDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "iconAPP")
    private String iconApp;

    @Column(name = "iconWEB")
    private String iconWeb;

    @Column(name = "PRICE", precision = 10, scale = 2)
    private Double price;

    @Column(name = "service_category_id")
    private Integer serviceCategoryId;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default false")
    private Boolean status = true;
}