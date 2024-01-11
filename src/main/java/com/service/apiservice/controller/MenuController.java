package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@Api(tags = "menu")
@RequiredArgsConstructor
public class MenuController {
//    @Autowired
//    MenuService menuService;
//
//    @PostMapping("/save-menu")
//    @ApiOperation("Insert menu")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<Boolean> saveMenu(@RequestBody MenuDTO menuDTO) {
//        return ResponseDataConfiguration.success(menuService.saveMenu(menuDTO));
//    }
//
//    @PostMapping("/get-menu")
//    @ApiOperation("Get menu")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<Boolean> getMenu() {
//        return ResponseDataConfiguration.success(false);
//    }
}
