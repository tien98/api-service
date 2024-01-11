package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data // Create getters and setters
@Table(name = "ORDERS")
@NoArgsConstructor
public class OrderDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "order_number")
    @ApiModelProperty(hidden = true)
    private String orderNumber;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "service_category_id")
    private Integer serviceCategoryId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "book_date")
    private Date bookDate;

    @Column(name = "book_time")
    private String bookTime;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "ward_id")
    private Integer wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "comments")
    private String comments;

    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Column(name = "order_normal_status_id")
    private Integer orderNormalStatusId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

    @Column(name = "subtotal")
    private Double subTotal;

    @Column(name = "total")
    private Double total;

    @Column(name = "acreage_type")
    private String acreageType;

    @Column(name = "date_type")
    private Integer dateType;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}