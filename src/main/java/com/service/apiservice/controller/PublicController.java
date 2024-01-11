package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.*;
import com.service.apiservice.model.*;
import com.service.apiservice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Api(tags = "API Public")
@RequiredArgsConstructor
public class PublicController {
    @Autowired
    ProvinceService provinceService;

    @Autowired
    ServiceService serviceHomestayService;

    @Autowired
    UserService userService;

    @Autowired
    ServiceCategoryTypeService serviceCategoryTypeService;

    @Autowired
    HolidayService holidayService;

    @Autowired
    EmailService emailService;

    @Autowired
    NewsService newsService;

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerDeviceTokenService customerDeviceTokenService;

    private final ModelMapper modelMapper;
    @GetMapping("/province/get-list")
    @ApiOperation("Get list province")
    public ResponseEntity<List<ProvinceDTO>> getProvinceList() {
        return ResponseDataConfiguration.success(provinceService.getList());
    }

    @GetMapping("/district/get-list")
    @ApiOperation("Get list district")
    public ResponseEntity<List<DistrictDTO>> getDistrictList(@RequestParam Integer provinceId) {
        return ResponseDataConfiguration.success(provinceService.getDistrictListByProvinceId(provinceId));
    }

    @GetMapping("/ward/get-list")
    @ApiOperation("Get list ward")
    public ResponseEntity<List<WardDTO>> getWardList(@RequestParam Integer districtId) {
        return ResponseDataConfiguration.success(provinceService.getWardListByProvinceId(districtId));
    }

    @GetMapping("/services/get-list")
    @ApiOperation("Get list service")
    public ResponseEntity<List<ServiceDTO>> getServiceList() {
        return ResponseDataConfiguration.success(serviceHomestayService.getList());
    }

    @GetMapping("/services/get-list-by-service-category")
    @ApiOperation("Get list service by categoryId")
    public ResponseEntity<List<ServiceDTO>> getServiceListByCategoryId(@RequestParam Integer serviceCategoryId) {
        return ResponseDataConfiguration.success(serviceHomestayService.getListByServiceCategoryId(serviceCategoryId));
    }

    @GetMapping("/users/send-code")
    @ApiOperation("Send code user")
    public ResponseEntity<CodeResponseDTO> sendCode(@RequestParam String userName) {
        CodeResponseDTO result = userService.sendCode(userName.trim());
        if(result.getIsSuccess()) {
            EmailDetailsDTO details = new EmailDetailsDTO();
            details.setRecipient(result.getMail());
            details.setSubject("Mã xác nhận");
            details.setMsgBody("Mã xác nhận của bạn là: " + result.getCode() + ". Vui lòng nhập mã trên vào ứng dụng GlobalSantech để xác nhận.");
            String resultSendMail = emailService.sendSimpleMail(details);
            if(!resultSendMail.isEmpty()) {
                result.setMessage(resultSendMail);
                result.setIsSuccess(false);
            }
        }

        return ResponseDataConfiguration.success(result);
    }

    @PostMapping("/users/reset-password")
    @ApiOperation("Reset password user")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO model) {
        return ResponseDataConfiguration.success(userService.resetPassword(model));
    }

    @PostMapping("/service-category-type/get-list")
    @ApiOperation("Service category type get all")
    public ResponseEntity<List<ServiceCategoryTypeTO>> getListServiceCategory() {
        return ResponseDataConfiguration.success(serviceCategoryTypeService.getList());
    }

    @PostMapping("/service-category-type/get-detail")
    @ApiOperation("Service category type get detail")
    public ResponseEntity<List<CategoryPriceResponseDTO>> getDetailServiceCategoryType(@RequestBody CategoryPriceRequestDTO model) {
        return ResponseDataConfiguration.success(serviceCategoryTypeService.getListCategoryPrice(model));
    }

    @GetMapping("/holiday/get-list")
    @ApiOperation("Service category type get detail")
    public ResponseEntity<List<HolidayDTO>> getHolidayList() {
        return ResponseDataConfiguration.success(holidayService.getList());
    }

    @PostMapping("/sendMail")
    public ResponseEntity<String>
    sendMail(@RequestBody EmailDetailsDTO details)
    {
        String status = emailService.sendSimpleMail(details);
        return ResponseDataConfiguration.success(status);
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public ResponseEntity<String> sendMailWithAttachment(@RequestBody EmailDetailsDTO details)
    {
        String status = emailService.sendMailWithAttachment(details);
        return ResponseDataConfiguration.success(status);
    }

    @GetMapping("/get-news")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "4") int size,
                                                      @RequestParam int type) throws IOException {
        Pageable paging = PageRequest.of(page, size, Sort.by("orderIndex"));
        return ResponseDataConfiguration.success(newsService.getAll(paging, type));
    }

    @PostMapping("/order/save-order")
    @ApiOperation("Public insert order")
    public ResponseEntity<Boolean> savePublicOrder(@RequestBody OrderRequestDTO orderDTO) {
        return ResponseDataConfiguration.success(orderService.saveOrder(modelMapper.map(orderDTO, OrderDTO.class)));
    }

    @PostMapping("/save-device-token")
    @ApiOperation("Save device token")
    public ResponseEntity<Boolean> saveDeviceToken(@RequestBody CustomerDeviceTokenDTO model) {
        return ResponseDataConfiguration.success(customerDeviceTokenService.save(model));
    }

    @PostMapping("/update-device-token")
    @ApiOperation("Update device token")
    public ResponseEntity<Boolean> updateDeviceToken(@RequestBody CustomerDeviceTokenDTO model) {
        return ResponseDataConfiguration.success(customerDeviceTokenService.update(model));
    }

    @PostMapping("/update-device-token-imei")
    @ApiOperation("Update device token imei")
    public ResponseEntity<Boolean> updateDeviceTokenByImei(@RequestBody CustomerDeviceTokenDTO model) {
        return ResponseDataConfiguration.success(customerDeviceTokenService.updateByImei(model));
    }
}
