package com.service.apiservice.service;

import com.service.apiservice.dto.FileResponseDTO;
import com.service.apiservice.dto.UploadFileReq;
import com.service.apiservice.dto.UploadFileRes;
import com.service.apiservice.model.MenuDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    UploadFileRes uploadFiles(List<UploadFileReq> uploadFileReq, MultipartFile[] multipartFile, int type);
    String getFile(Integer fileId, Integer type) throws IOException;
    String getFile(String fileName) throws IOException;
    FileResponseDTO uploadFile(MultipartFile file);
}
