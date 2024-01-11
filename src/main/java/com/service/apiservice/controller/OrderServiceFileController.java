package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.OrderServiceFileRequestDTO;
import com.service.apiservice.dto.OrderServiceFileResponseDTO;
import com.service.apiservice.service.OrderServiceFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order-service-file")
@Api(tags = "OrderServiceFile")
@RequiredArgsConstructor
public class OrderServiceFileController {
    @Autowired
    OrderServiceFileService orderServiceFileService;

    @PostMapping("/save")
    @ApiOperation("Save id file")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> save(@RequestBody OrderServiceFileRequestDTO model) {
        return ResponseDataConfiguration.success(orderServiceFileService.save(model));
    }

    @GetMapping("/get-file")
    @ApiOperation("Get file")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<OrderServiceFileResponseDTO>> getFiles(@RequestParam int orderId, @RequestParam int type) throws IOException  {
        return ResponseDataConfiguration.success(orderServiceFileService.getFiles(orderId, type));
    }
}
