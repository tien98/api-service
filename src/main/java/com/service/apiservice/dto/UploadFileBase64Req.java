package com.service.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileBase64Req {
    private List<Item> listFile;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String fileName;
        private String type;
        private String dataBase64;
        private String fmsApplicationId;
    }
}
