package com.service.apiservice.service.impl;

import com.service.apiservice.constants.DateTimeConstants;
import com.service.apiservice.dto.FileResponseDTO;
import com.service.apiservice.dto.UploadFileDTO;
import com.service.apiservice.dto.UploadFileReq;
import com.service.apiservice.dto.UploadFileRes;
import com.service.apiservice.exception.BusinessException;
import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.model.OrderServiceFileDTO;
import com.service.apiservice.repository.MenuRepository;
import com.service.apiservice.repository.OrderServiceFileRepository;
import com.service.apiservice.service.FileService;
import com.service.apiservice.service.MenuService;
import com.service.apiservice.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    OrderServiceFileRepository orderServiceFileRepository;
    private final List<String> allowExtensions = Arrays.asList(
            "doc", "docx", "xls", "xlsx", "pdf", "ppt", "pptx", "zip", "rar", "jpg", "jpe", "jpeg", "gif", "png", "bmp",
            "tif", "tiff", "txt", "csv", "mp3", "mp4", "cdr"
    );
    private static final String UPLOAD_DIR_CHECKIN = "./uploads/checkin/";
    private static final String UPLOAD_DIR_CHECKOUT = "./uploads/checkout/";
    private static final String UPLOAD_DIR_IMAGE = "./uploads/image/";
    @Override
    public UploadFileRes uploadFiles(List<UploadFileReq> uploadFileReq, MultipartFile[] multipartFile, int type) {
        UploadFileRes uploadFileRes = new UploadFileRes();
        List<UploadFileDTO> uploadFileDTOS = new ArrayList<>();
        List<String> errorUploadFileNames = new ArrayList<>();
        for (int i = 0; i < multipartFile.length; ++i) {
            try {
                UploadFileDTO uploadFileDTO = uploadFile(uploadFileReq.get(i), multipartFile[i], type);
                if (Objects.nonNull(uploadFileDTO)) {
                    uploadFileDTOS.add(uploadFileDTO);
                } else {
                    errorUploadFileNames.add(uploadFileReq.get(i).getFileName());
                }
            } catch (Exception ex) {
                errorUploadFileNames.add(uploadFileReq.get(i).getFileName());
            }
        }
        uploadFileRes.setUploadFiles(uploadFileDTOS);
        uploadFileRes.setErrorUploadFileNames(errorUploadFileNames);
        return uploadFileRes;
    }

    @Override
    public String getFile(Integer fileId, Integer type) throws IOException {
        return getBase64File(fileId);
    }

    @Override
    public String getFile(String fileName) throws IOException {
        return getBase64FileByFileName(fileName);
    }

    @Override
    public FileResponseDTO uploadFile(MultipartFile file) {
        FileResponseDTO result = new FileResponseDTO();
        if (file.isEmpty()) {
            result.setIsSuccess(false);
            result.setContent("Failed to store empty file");
            return result;
        }

        String partFileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String formatFileName =
                String.format("%s_%s%s", UUID.randomUUID(), System.currentTimeMillis(), ".".concat(partFileExtension));
        StringBuilder filePath = new StringBuilder();
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR_IMAGE);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        filePath.append(UPLOAD_DIR_IMAGE)
                .append(formatFileName);
        try (InputStream inputStream = file.getInputStream()) {
            File targetFile = new File(filePath.toString());
            Files.copy(inputStream, targetFile.toPath());
        } catch (IOException e) {
            result.setIsSuccess(false);
            result.setContent(e.getMessage());
            return result;
        }
        result.setIsSuccess(true);
        result.setContent(formatFileName);
        return result;
    }
    private String getBase64FileByFileName(String fileName) throws IOException {
        if(fileName == null || fileName.isEmpty())
            return "";
        String filePath = UPLOAD_DIR_IMAGE + fileName;
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }
    private String getBase64File(Integer fileId) throws IOException {
        Optional<OrderServiceFileDTO> optionalOrderServiceFileDTO = orderServiceFileRepository.findById(fileId);
        if(optionalOrderServiceFileDTO.isEmpty())
            throw new BusinessException("0501002");

//        StringBuilder filePath = new StringBuilder();
//        filePath.append(type == 1 ? UPLOAD_DIR_CHECKIN : UPLOAD_DIR_CHECKOUT);
        String filePath = optionalOrderServiceFileDTO.get().getFilePath();
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private UploadFileDTO uploadFile(UploadFileReq uploadFileReq, MultipartFile multipartFile, int type) {
        if (!StringUtils.hasLength(uploadFileReq.getFileName())) {
            throw new BusinessException("0501002");
        }
        String partFileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String partFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
        Date currentDate = new Date();

        if (!uploadFileReq.getFileName().equals(partFileName)) {
            throw new BusinessException("0501003", Collections.singletonList(multipartFile.getOriginalFilename()));
        }

        // Create the upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR_CHECKIN);
        if (type == 2) {
            uploadDir = new File(UPLOAD_DIR_CHECKOUT);
        }

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String formatFileName =
                String.format("%s_%s%s", UUID.randomUUID(), System.currentTimeMillis(), ".".concat(partFileExtension));

        StringBuilder filePath = new StringBuilder();
        filePath.append(type == 1 ? UPLOAD_DIR_CHECKIN : UPLOAD_DIR_CHECKOUT)
                .append(formatFileName);

        // Save the file to the server
        try (InputStream inputStream = multipartFile.getInputStream()) {
            File targetFile = new File(filePath.toString());
            Files.copy(inputStream, targetFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return UploadFileDTO.builder()
                .fileId("")
                .fmsApplicationId("")
                .fileName(formatFileName)
                .filePath(filePath.toString())
                .createUser("")
                .createDate(currentDate)
                .size(multipartFile.getSize())
                .description(StringUtils.hasLength(uploadFileReq.getDescription()) ? uploadFileReq.getDescription().trim() : null)
                .build();
    }
}
