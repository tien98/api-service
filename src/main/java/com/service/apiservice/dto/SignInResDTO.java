package com.service.apiservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInResDTO {
    private boolean isSuccess;
    private String token;
    private String message;
}
