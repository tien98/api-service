package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.*;
import com.service.apiservice.model.EvaluateDTO;
import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.model.OrderDTO;
import com.service.apiservice.service.MenuService;
import com.service.apiservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = "Order dich vu")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;

    private final ModelMapper modelMapper;

    @PostMapping("/save-order")
    @ApiOperation("Insert order")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> saveMenu(@RequestBody OrderRequestDTO orderDTO) {
        return ResponseDataConfiguration.success(orderService.saveOrder(modelMapper.map(orderDTO, OrderDTO.class)));
    }

    @PostMapping("/update-status")
    @ApiOperation("Update status order")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> updateStatus(@RequestBody UpdateStatusRequestDTO model) {
        return ResponseDataConfiguration.success(orderService.updateStatus(model));
    }

    @PostMapping("/get-list")
    @ApiOperation("Get List")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<OrderSearchResponseDTO>> getList(@RequestBody OrderSearchRequestDTO model, HttpServletRequest httpServlet) {
        return ResponseDataConfiguration.success(orderService.getOrderList(model, httpServlet));
    }

    @GetMapping("/get-one")
    @ApiOperation("Get List")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<OrderDTO> getOne(@RequestParam Integer id) {
        return ResponseDataConfiguration.success(orderService.getOne(id));
    }

    @PostMapping("/rate")
    @ApiOperation("Rate order")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> rateOrder(@RequestBody RateRequestDTO body) {
        return ResponseDataConfiguration.success(orderService.rateOrder(body));
    }

    @GetMapping("/get-rate")
    @ApiOperation("Get rate order")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<EvaluateDTO>> getRateOrder(@RequestParam Integer orderId) {
        return ResponseDataConfiguration.success(orderService.getRateOrder(orderId));
    }

}
