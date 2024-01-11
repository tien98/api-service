package com.service.apiservice.service;

import com.service.apiservice.dto.OrderServiceFileRequestDTO;
import com.service.apiservice.dto.OrderServiceFileResponseDTO;
import com.service.apiservice.dto.UploadFileReq;
import com.service.apiservice.dto.UploadFileRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrderServiceFileService {
    Boolean save(OrderServiceFileRequestDTO model);
    List<OrderServiceFileResponseDTO> getFiles(int orderId, int type) throws IOException;
}
