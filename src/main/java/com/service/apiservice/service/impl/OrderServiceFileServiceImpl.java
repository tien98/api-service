package com.service.apiservice.service.impl;

import com.service.apiservice.dto.*;
import com.service.apiservice.exception.BusinessException;
import com.service.apiservice.model.OrderServiceFileDTO;
import com.service.apiservice.repository.OrderServiceFileRepository;
import com.service.apiservice.service.FileService;
import com.service.apiservice.service.OrderServiceFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

@Service
@Transactional
public class OrderServiceFileServiceImpl implements OrderServiceFileService {
    @Autowired
    OrderServiceFileRepository orderServiceFileRepository;

    @Autowired
    FileService fileService;

    @Override
    public Boolean save(OrderServiceFileRequestDTO model) {
        try{
            var listFile = model.getUploadFiles();
            for (var item : listFile) {
                OrderServiceFileDTO orderServiceFileDTO = new OrderServiceFileDTO();
                orderServiceFileDTO.setOrderId(model.getOrderId());
                orderServiceFileDTO.setEventId(model.getEventId());
                orderServiceFileDTO.setFileName(item.getFileName());
                orderServiceFileDTO.setFilePath(item.getFilePath());
                orderServiceFileDTO.setFileSize(item.getSize().toString());
                orderServiceFileRepository.save(orderServiceFileDTO);
            }
            return true;
        }catch (Exception ex) {
           return false;
        }
    }

    @Override
    public List<OrderServiceFileResponseDTO> getFiles(int orderId, int type) throws IOException {
        List<OrderServiceFileResponseDTO> lstFile = new ArrayList<>();
        var lstOrderFile = orderServiceFileRepository.getAllByOrderIdIsAndEventIdIs(orderId, type);
        if(lstOrderFile.isEmpty())
            return null;

        for (var item : lstOrderFile) {
            String fileContent = fileService.getFile(item.getId(), type);
            lstFile.add(OrderServiceFileResponseDTO.builder()
                            .createdAt(item.getCreatedAt())
                    .fileName(item.getFileName())
                    .fileContent(fileContent)
                    .build());
        }
        return lstFile;
    }
}
