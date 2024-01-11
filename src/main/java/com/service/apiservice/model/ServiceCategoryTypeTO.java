package com.service.apiservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "service_category_type")
@NoArgsConstructor
public class ServiceCategoryTypeTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    /*
    * 1 Ngày tháng
    * 2 Diện tích
    * */
    @Column(name = "type_option")
    private Integer typeOption;

    /*
     * 1 Ngày thường
     * 2 Ngày lễ
     * 3 Giờ lưu động
     * */
    @Column(name = "type")
    private Integer type;

    @Column(name = "service_category_id")
    private Integer serviceCategoryId;

    @Column(name = "status", columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}