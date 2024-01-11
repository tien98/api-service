package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderServiceFileResponseDTO {
    private String fileName;
    private String fileContent;
    private String createdBy;
    private String lastUpdatedBy;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
