package com.service.apiservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderServiceFileRequestDTO {
    private List<UploadFileDTO> uploadFiles;
    private int orderId;
    private int eventId;
}
