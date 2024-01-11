package com.service.apiservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "service_type_price")
@NoArgsConstructor
public class ServiceTypePriceTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "price", precision = 10, scale = 2)
    private Double price;

    @Column(name = "note")
    private String note;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @Column(name = "service_category_type_id")
    private Integer serviceCategoryTypeId;

    @Column(name = "status", columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}