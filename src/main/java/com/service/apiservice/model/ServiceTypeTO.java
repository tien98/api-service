package com.service.apiservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "service_type")
@NoArgsConstructor
public class ServiceTypeTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "type")
    private Integer type;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "status", columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}