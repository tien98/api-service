package com.service.apiservice.service.impl;

import com.service.apiservice.dto.FCMNotificationDTO;
import com.service.apiservice.dto.FCMRequestDTO;
import com.service.apiservice.model.*;
import com.service.apiservice.repository.*;
import com.service.apiservice.service.EmailService;
import com.service.apiservice.service.NotificationService;
import com.service.apiservice.service.OrderService;
import com.service.apiservice.service.UserOrderService;
import com.service.apiservice.utils.DataUtil;
import com.service.apiservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.service.apiservice.utils.DataUtil.isObjectNull;

@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserOrderRepository userOrderRepository;
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    NotificationCustomRepository notificationCustomRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserNotificationRepository userNotificationRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    CustomerDeviceTokenRepository customerDeviceTokenRepository;
    private final String NAME_NOTIFY = "Bạn nhận được đơn đặt dịch vụ";
    private final String CONTENT_NOTIFY = "Khách hàng đã đặt dịch vụ: ";
    private final String NAME_NOTIFY_CONFIRM_PAYMENT = "Bạn nhận được yêu cầu thanh toán dịch vụ";
    private final String CONTENT_NOTIFY_CONFIRM_PAYMENT = "Thanh toán dịch vụ: ";
    private final String NAME_NOTIFY_STAFF = "Dịch vụ dọn dẹp";
    private final String CONTENT_NOTIFY_STAFF = "Bạn nhận được lịch dọn dẹp dịch vụ: ";
    @Transactional
    public String saveUserOrder(UserOrderDTO order){
        try{
            var orderOne = orderRepository.getById(order.getOrderId());
            if (orderOne == null) {
                return "Mã dịch vụ không tồn tại";
            }
            if (orderOne.getOrderStatusId() == DataUtil.ORDER_STATUS.TU_CHOI.getValue()) {
                return "Đơn dịch vụ đã từ chối. Lý do: " + orderOne.getReason();
            }
            Boolean checkTransferOrder = checkExistsTransferOrder(order.getOrderId(), order.getUserId());
            if (checkTransferOrder) {
                return "Dịch vụ đã được gửi 1 lần trước đó cho tài khoản này";
            }
            userOrderRepository.save(order);

            ServiceCategoryDTO serviceCategoryDTO = serviceCategoryRepository.getById(orderOne.getServiceCategoryId());
            if(isObjectNull(serviceCategoryDTO)){
                return "Dịch vụ không tồn tại";
            }

            String nameNotify = NAME_NOTIFY;
            if(order.getOrderStatusId() == DataUtil.ORDER_STATUS.SALE_YEU_CAU_THANH_TOAN.getValue()) {
                nameNotify = NAME_NOTIFY_CONFIRM_PAYMENT;
            }
            if(order.getOrderStatusId() == DataUtil.ORDER_STATUS.TO_STAFF.getValue()) {
                nameNotify = NAME_NOTIFY_STAFF;
            }

            String contentNotify = CONTENT_NOTIFY + serviceCategoryDTO.getName();
            if(order.getOrderStatusId() == DataUtil.ORDER_STATUS.SALE_YEU_CAU_THANH_TOAN.getValue()) {
                contentNotify = CONTENT_NOTIFY_CONFIRM_PAYMENT + orderOne.getOrderNumber() + " - " + serviceCategoryDTO.getName();
            }
            if(order.getOrderStatusId() == DataUtil.ORDER_STATUS.TO_STAFF.getValue()) {
                contentNotify = CONTENT_NOTIFY_STAFF + serviceCategoryDTO.getName();
            }
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setName(nameNotify);
            notificationDTO.setType(DataUtil.TYPE_NOTIFY.REGISTER.getValue());
            notificationDTO.setTypeOption(DataUtil.TYPE_OPTION_NOTIFY.NOW.getValue());
            notificationDTO.setTarget(DataUtil.TARGET_NOTIFY.ALL.getValue());
            notificationDTO.setStatus(true);
            notificationDTO.setFlagOrder(2);
            notificationDTO.setOrderId(order.getOrderId()
            );
            notificationDTO.setContent(contentNotify);
            notificationCustomRepository.sendNotify(notificationDTO, order.getUserId());
            sendFCM(order.getUserId(), contentNotify, nameNotify);
            return "SUCCESS";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private Boolean checkExistsTransferOrder(int orderId, int userId) {
        List<NotificationDTO> lstNotification = notificationRepository.findByOrderIdAndFlagOrder(orderId, 2);
        if (lstNotification == null)
            return false;

        for (var item : lstNotification) {
            var listUserNotify = userNotificationRepository.getAllByCustomerIdAndNotifyId(userId, item.getId());
            if(!listUserNotify.isEmpty()){
                return true;
            }
        }
        return false;
    }
    private void sendFCM(Integer userId, String body, String title){
        List<CustomerDeviceTokenDTO> list = customerDeviceTokenRepository.getAllByUserIdIsAndDeviceTokenNotNull(userId);
        if (list != null) {
            for (var i : list) {
                FCMRequestDTO fcmRequestDTO = new FCMRequestDTO();
                fcmRequestDTO.setTo(i.getDeviceToken());
                FCMNotificationDTO fcmNotification = new FCMNotificationDTO();
                fcmNotification.setBody(body);
                fcmNotification.setTitle(title);
                fcmRequestDTO.setNotification(fcmNotification);
                emailService.sendFCM(fcmRequestDTO);
            }
        }
    }
}
