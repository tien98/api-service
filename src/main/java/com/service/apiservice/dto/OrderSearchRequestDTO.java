package com.service.apiservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderSearchRequestDTO {
    @NotNull
    private Long fromDate;
    @NotNull
    private Long toDate;
    private String keySearch;
    private Integer statusId;
}
