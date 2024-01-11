package com.service.apiservice.service.impl;

import com.service.apiservice.dto.DetailNotificationDTO;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.repository.NotificationRepository;
import com.service.apiservice.repository.OrderRepository;
import com.service.apiservice.repository.UserNotificationRepository;
import com.service.apiservice.repository.UserRepository;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Autowired
    OrderRepository orderRepository;
    public Map<String, Object> getAllNotification(Pageable pageable, HttpServletRequest req){
        var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        Map<String, Object> response = new HashMap<>();
        List<NotificationDTO> notificationDTOList = new ArrayList<NotificationDTO>();
        if (user != null) {
            if (user.getAppUserRoles().contains(AppUserRole.ROLE_SUPER_ADMIN)) {
                Page<NotificationDTO> pageNotifications;
                pageNotifications = notificationRepository.findAllByFlagOrderEquals(pageable, 1);
                notificationDTOList = pageNotifications.getContent();
                response.put("notifications", notificationDTOList);
                response.put("currentPage", pageNotifications.getNumber());
                response.put("totalItems", pageNotifications.getTotalElements());
                response.put("totalPages", pageNotifications.getTotalPages());
                return response;
            } else {
                var pageUserNotifications = userNotificationRepository.findAllByCustomerId(pageable, user.getId());
                if (!pageUserNotifications.isEmpty()) {
                    for (var item : pageUserNotifications) {
                        NotificationDTO notificationDTO;
                        if (notificationRepository.findById(item.getNotifyId()).isPresent()) {
                            notificationDTO = notificationRepository.findById(item.getNotifyId()).get();
                            notificationDTOList.add(notificationDTO);
                        }
                    }

                    response.put("notifications", notificationDTOList);
                    response.put("currentPage", pageUserNotifications.getNumber());
                    response.put("totalItems", pageUserNotifications.getTotalElements());
                    response.put("totalPages", pageUserNotifications.getTotalPages());
                    return response;
                }else {
                    response.put("notifications", notificationDTOList);
                    response.put("currentPage", pageUserNotifications.getNumber());
                    response.put("totalItems", pageUserNotifications.getTotalElements());
                    response.put("totalPages", pageUserNotifications.getTotalPages());
                }
            }
        } else {
            response.put("notifications", notificationDTOList);
            response.put("totalPages", 0);
        }
        return response;
    }

    public Integer getNotificationsUnRead(int userId, HttpServletRequest req) {
        var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        if (user != null) {
            if (user.getAppUserRoles().contains(AppUserRole.ROLE_SUPER_ADMIN)) {
                var list = notificationRepository.findAllByIsReadIsFalse();
                return list.isEmpty() ? 0 : list.size();
            }else {
                var list = userNotificationRepository.getAllByCustomerId(userId);
                if (list.isEmpty())
                    return 0;
                int count = 0;
                for (var item : list) {
                    var notify = notificationRepository.findById(item.getNotifyId());
                    if (notify.isPresent()){
                        var notifyDTO = notify.get();
                        if(!notifyDTO.getIsRead()) {
                            count++;
                        }
                    }
                }
                return count;
            }
        }
        return 0 ;
    }

    public Boolean updateReadNotify(int notificationId, boolean isRead) {
        try {
            var notify = notificationRepository.findById(notificationId);
            if(notify == null) {
                return true;
            }
            notify.setIsRead(isRead);
            notificationRepository.save(notify);
            return true;
        }catch (Exception ex) {
            return  false;
        }
    }

    public DetailNotificationDTO getDetailNotification(int notificationId) {
        DetailNotificationDTO detailNotificationDTO = new DetailNotificationDTO();
        NotificationDTO notificationDTO = notificationRepository.findById(notificationId);
        detailNotificationDTO.setNotificationDTO(notificationDTO);
        var order = orderRepository.findById(notificationDTO.getOrderId());
        detailNotificationDTO.setOrderDTO(order.get());
        return detailNotificationDTO;
    }
}
