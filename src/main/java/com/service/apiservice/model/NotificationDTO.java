package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data // Create getters and setters
@Table(name = "NOTIFICATIONS")
@NoArgsConstructor
public class NotificationDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "`type`")
    private Integer type;

    @Column(name = "content")
    private String content;

    @Column(name = "type_option")
    private Integer typeOption;

    @Column(name = "schedule")
    private Date schedule;

    @Column(name = "target")
    private Integer target;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "flag_order")
    private Integer flagOrder = 1;

    @Column(name = "is_read",  columnDefinition = "Bit(1) default false")
    private Boolean isRead = false;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}