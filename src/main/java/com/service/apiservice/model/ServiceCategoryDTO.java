package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data // Create getters and setters
@Table(name = "SERVICE_CATEGORIES")
@NoArgsConstructor
public class ServiceCategoryDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "iconAPP")
    private String iconApp;

    @Column(name = "iconWEB")
    private String iconWeb;

    @Column(name = "pathScreen")
    private String pathScreen;

    @Column(name = "code")
    private String code;

    @Column(name = "PRICE", precision = 10, scale = 2)
    private Double price;

    @Column(name = "price_promotion", precision = 10, scale = 2)
    private Double pricePromotion;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}