package com.service.apiservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UploadFileRes {
    private List<UploadFileDTO> uploadFiles;
    private List<String> errorUploadFileNames;
}
