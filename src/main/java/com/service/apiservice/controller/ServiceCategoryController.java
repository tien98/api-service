package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.service.MenuService;
import com.service.apiservice.service.ServiceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@Api(tags = "API Public")
@RequiredArgsConstructor
public class ServiceCategoryController {
    @Autowired
    ServiceCategoryService serviceCategoryService;

    @GetMapping("/service-category/get-list")
    @ApiOperation("Get list service")
    public ResponseEntity<List<ServiceCategoryDTO>> getList() {
        return ResponseDataConfiguration.success(serviceCategoryService.getList());
    }

    @GetMapping("/service-category-get")
    @ApiOperation("Get one service category")
    public ResponseEntity<ServiceCategoryDTO> getOne(@RequestParam Integer serviceCategoryId) {
        return ResponseDataConfiguration.success(serviceCategoryService.getOne(serviceCategoryId));
    }
}
