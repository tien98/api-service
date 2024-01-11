package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.*;
import com.service.apiservice.model.OrderDTO;
import com.service.apiservice.service.FileService;
import com.service.apiservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@Api(tags = "File")
@RequiredArgsConstructor
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/uploads-checkin")
    @ApiOperation("Upload image")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UploadFileRes> uploadCheckIn(@RequestBody UploadFileBase64Req req) {
        List<UploadFileBase64Req.Item> listFile = req.getListFile();
        List<UploadFileReq> uploadFileDTOS = listFile.stream().map(file -> UploadFileReq.builder()
                .fileName(file.getFileName())
                .fmsApplicationId(file.getFmsApplicationId())
                .build()).collect(Collectors.toList());
        List<BASE64DecodedMultipartFile> listMultipartFile = listFile.stream()
                .map(file -> new BASE64DecodedMultipartFile(
                        Base64.decodeBase64(
                                (file.getDataBase64().substring(file.getDataBase64().indexOf(",")+1)).getBytes()
                        ),
                        file.getFileName(),
                        file.getType()
                ))
                .collect(Collectors.toList());
        BASE64DecodedMultipartFile[] multipartFile = new BASE64DecodedMultipartFile[listMultipartFile.size()];
        multipartFile = listMultipartFile.toArray(multipartFile);

        return ResponseDataConfiguration.success(fileService.uploadFiles(uploadFileDTOS, multipartFile, 1));
    }

    @PostMapping("/uploads-checkout")
    @ApiOperation("Upload image")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UploadFileRes> uploadCheckOut(@RequestBody UploadFileBase64Req req) {
        List<UploadFileBase64Req.Item> listFile = req.getListFile();
        List<UploadFileReq> uploadFileDTOS = listFile.stream().map(file -> UploadFileReq.builder()
                .fileName(file.getFileName())
                .fmsApplicationId(file.getFmsApplicationId())
                .build()).collect(Collectors.toList());
        List<BASE64DecodedMultipartFile> listMultipartFile = listFile.stream()
                .map(file -> new BASE64DecodedMultipartFile(
                        Base64.decodeBase64(
                                (file.getDataBase64().substring(file.getDataBase64().indexOf(",")+1)).getBytes()
                        ),
                        file.getFileName(),
                        file.getType()
                ))
                .collect(Collectors.toList());
        BASE64DecodedMultipartFile[] multipartFile = new BASE64DecodedMultipartFile[listMultipartFile.size()];
        multipartFile = listMultipartFile.toArray(multipartFile);

        return ResponseDataConfiguration.success(fileService.uploadFiles(uploadFileDTOS, multipartFile, 2));
    }

    @GetMapping("/get-file-checkin")
    @ApiOperation("Get file base64")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> getBase64File(@RequestParam Integer fileId, @RequestParam Integer type) throws IOException {
        return ResponseDataConfiguration.success(fileService.getFile(fileId, type));
    }

    @PostMapping("/upload")
    @ApiOperation("Upload file")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<FileResponseDTO> getBase64File(@RequestParam MultipartFile file){
        return ResponseDataConfiguration.success(fileService.uploadFile(file));
    }
}
