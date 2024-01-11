package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BaseDTO {
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private String lastUpdatedBy;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp lastUpdatedAt;
}
