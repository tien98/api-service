package com.service.apiservice.dto;

import com.service.apiservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewResponseDTO {
    private Integer newsId;

    private String newsImage;

    private String newsTitle;

    private String newsDescription;

    private String newsContent;

    private String newsLink;

    private Integer type;

    private Integer orderIndex;

    private Integer isActive = 1;
}