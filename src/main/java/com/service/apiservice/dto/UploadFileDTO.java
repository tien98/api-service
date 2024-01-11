package com.service.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.service.apiservice.constants.DateTimeConstants.DATE_TIME_FORMAT;
import static com.service.apiservice.constants.DateTimeConstants.TIME_ZONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadFileDTO {
    private String fmsApplicationId;
    private String fileId;
    private String fileName;
    private String filePath;
    private String createUser;
    @JsonFormat(pattern=DATE_TIME_FORMAT, timezone = TIME_ZONE)
    private Date createDate;
    private String description;
    private Long size;
}
