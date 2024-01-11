package com.service.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMailDTO {
    private String from;
    private String to;
    private String msgBody;
    private String subject;
}
