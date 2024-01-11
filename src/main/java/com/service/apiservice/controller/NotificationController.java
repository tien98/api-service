package com.service.apiservice.controller;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.ContactMailDTO;
import com.service.apiservice.dto.DetailNotificationDTO;
import com.service.apiservice.dto.EmailDetailsDTO;
import com.service.apiservice.dto.FCMRequestDTO;
import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.service.EmailService;
import com.service.apiservice.service.NotificationService;
import com.service.apiservice.utils.RestApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/notification")
@Api(tags = "Thông báo")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    EmailService emailService;
    @GetMapping("/get-all")
    @ApiOperation("Get all notification")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      HttpServletRequest req) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseDataConfiguration.success(notificationService.getAllNotification(paging, req));
    }

    @GetMapping("/get-all-unread")
    @ApiOperation("Get all notification unread")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Integer> getAllUnread(@RequestParam int userId, HttpServletRequest req) {
        return ResponseDataConfiguration.success(notificationService.getNotificationsUnRead(userId, req));
    }

    @GetMapping("/read-notify")
    @ApiOperation("Update read notify")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Boolean> readNotify(@RequestParam int notificationId, @RequestParam(defaultValue = "true") boolean isRead) {
        return ResponseDataConfiguration.success(notificationService.updateReadNotify(notificationId, isRead));
    }

    @GetMapping("/get-detail")
    @ApiOperation("Get detail notify")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<DetailNotificationDTO> getDetail(@RequestParam int notificationId) {
        return ResponseDataConfiguration.success(notificationService.getDetailNotification(notificationId));
    }

    @PostMapping("/sendMailContact")
    @ApiOperation("Contact")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> sendMailContact(@RequestBody ContactMailDTO details)
    {
        String status = emailService.sendMailContact(details);
        return ResponseDataConfiguration.success(status);
    }

    @PostMapping("/send-fcm")
    @ApiOperation("FCM")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_STAFF') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<Object> sendFCM(@RequestBody FCMRequestDTO request)
    {
        return ResponseDataConfiguration.success(emailService.sendFCM(request));
    }
}
