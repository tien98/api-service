package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data // Create getters and setters
@Table(name = "CUSTOMER_NOTIFICATIONS")
@NoArgsConstructor
public class UserNotificationDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "notify_id")
    private Integer notifyId;

    @Column(name = "STATUS",  columnDefinition = "Bit(1) default true")
    private Boolean status = true;
}