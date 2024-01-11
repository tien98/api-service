package com.service.apiservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "wards")
@NoArgsConstructor
public class WardDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wards_id")
    private Integer wardsId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "name")
    private String name;
}