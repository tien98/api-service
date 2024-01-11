package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.UserOrderRequestDTO;
import com.service.apiservice.model.UserOrderDTO;
import com.service.apiservice.service.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-order")
@Api(tags = "Chuyển dich vu qua các user")
@RequiredArgsConstructor
public class UserOrderController {
    @Autowired
    UserOrderService userOrderService;

    private final ModelMapper modelMapper;

    @PostMapping("/save-user-order")
    @ApiOperation("Insert user order")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> saveMenu(@RequestBody UserOrderRequestDTO userOrderDTO) {
        return ResponseDataConfiguration.success(userOrderService.saveUserOrder(modelMapper.map(userOrderDTO, UserOrderDTO.class)));
    }
}
