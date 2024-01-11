package com.service.apiservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // Create getters and setters
@Table(name = "ORDER_SERVICE_FILES")
@NoArgsConstructor
public class OrderServiceFileDTO extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_path")
    private String filePath;
}