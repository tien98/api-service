package com.service.apiservice.service.impl;

import com.service.apiservice.dto.*;
import com.service.apiservice.model.*;
import com.service.apiservice.repository.*;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.service.EmailService;
import com.service.apiservice.service.OrderService;
import com.service.apiservice.service.ServiceCategoryService;
import com.service.apiservice.utils.DataUtil;
import com.service.apiservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.service.apiservice.utils.DataUtil.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserOrderRepository userOrderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationCustomRepository notificationCustomRepository;
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    EmailService emailService;
    @Autowired
    CustomerDeviceTokenRepository customerDeviceTokenRepository;
    @Autowired
    RateRepository rateRepository;
    private final String NAME_NOTIFY = "Đã đăng ký dọn dẹp Homestay";
    @Transactional
    public Boolean saveOrder(OrderDTO order){
        try{
            if(order.getOrderNumber() == null) {
                String orderNumber = DateUtil.nowUp();
                order.setOrderNumber("DH_" + orderNumber);
            }

            List<AppUser> listUserSuperAdmin = userRepository.findByAppUserRolesEquals(AppUserRole.ROLE_SUPER_ADMIN);
            if(isObjectNull(listUserSuperAdmin)) {
                return false;
            }
            ServiceCategoryDTO serviceCategoryDTO = serviceCategoryRepository.getById(order.getServiceCategoryId());
            if(isObjectNull(serviceCategoryDTO)){
                return false;
            }
            OrderDTO getOrderDTO = orderRepository.saveAndFlush(order);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setName(NAME_NOTIFY);
            notificationDTO.setType(DataUtil.TYPE_NOTIFY.REGISTER.getValue());
            notificationDTO.setTypeOption(DataUtil.TYPE_OPTION_NOTIFY.NOW.getValue());
            notificationDTO.setTarget(DataUtil.TARGET_NOTIFY.ALL.getValue());
            notificationDTO.setStatus(true);
            notificationDTO.setOrderId(getOrderDTO.getId());
            notificationDTO.setFlagOrder(1);
            notificationDTO.setContent("Khách hàng đã đặt dịch vụ: "+ serviceCategoryDTO.getName());
            sendFCM(listUserSuperAdmin,"Khách hàng đã đặt dịch vụ: "+ serviceCategoryDTO.getName(), "Đơn đặt dịch vụ");
            return notificationCustomRepository.saveNotify(notificationDTO, listUserSuperAdmin);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional
    public Boolean updateStatus(UpdateStatusRequestDTO model) {
        try {
            if (model == null) return false;
            if (model.getOrderId() == null) return false;

            var optionalOrder = orderRepository.findById(model.getOrderId());
            if (optionalOrder.isPresent()) {
                var order = optionalOrder.get();
                order.setOrderStatusId(model.getStatusId());
                order.setReason(model.getReason());
                if (model.getStatusId() == DataUtil.ORDER_STATUS.SALE_DA_THU_TIEN.getValue()) {
                   order.setPaymentMethodId(1);
                }
                orderRepository.save(order);

                if (model.getStatusId() == DataUtil.ORDER_STATUS.SALE_DA_THU_TIEN.getValue()) {
                    List<AppUser> listUserAdmin = userRepository.findByAppUserRolesEquals(AppUserRole.ROLE_ADMIN);
                    if(isObjectNull(listUserAdmin)) {
                        return false;
                    }
                    NotificationDTO notificationDTO = new NotificationDTO();
                    notificationDTO.setName("Nhân viên sale đã thu tiền");
                    notificationDTO.setType(DataUtil.TYPE_NOTIFY.REGISTER.getValue());
                    notificationDTO.setTypeOption(DataUtil.TYPE_OPTION_NOTIFY.NOW.getValue());
                    notificationDTO.setTarget(DataUtil.TARGET_NOTIFY.ALL.getValue());
                    notificationDTO.setStatus(true);
                    notificationDTO.setOrderId(model.getOrderId());
                    notificationDTO.setFlagOrder(3);
                    notificationDTO.setContent("Nhân viên sale đã thu tiền đơn hàng: " + order.getOrderNumber());
                    sendFCM(listUserAdmin,"Nhân viên sale đã thu tiền đơn hàng: " + order.getOrderNumber(), "Sale đã thu tiền");
                    notificationCustomRepository.saveNotify(notificationDTO, listUserAdmin);
                }

                if (model.getStatusId() == ORDER_STATUS.STAFF_UP_FILE_CHECK_OUT.getValue() && order.getUserId() != null && order.getUserId() != 0) {
                    NotificationDTO notificationDTO = new NotificationDTO();
                    notificationDTO.setName("Nhân viên dọn dẹp xong dịch vụ");
                    notificationDTO.setType(DataUtil.TYPE_NOTIFY.REGISTER.getValue());
                    notificationDTO.setTypeOption(DataUtil.TYPE_OPTION_NOTIFY.NOW.getValue());
                    notificationDTO.setTarget(DataUtil.TARGET_NOTIFY.ALL.getValue());
                    notificationDTO.setStatus(true);
                    notificationDTO.setOrderId(model.getOrderId());
                    notificationDTO.setFlagOrder(4);
                    notificationDTO.setContent("Nhân viên dọn dẹp xong dịch vụ: " + order.getOrderNumber());
                    sendOneFCM(order.getUserId(),"Nhân viên dọn dẹp xong dịch vụ: " + order.getOrderNumber(), "Nhân viên dọn dẹp xong dịch vụ");
                    notificationCustomRepository.sendNotify(notificationDTO, order.getUserId());
                }
            }
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    public List<OrderSearchResponseDTO>  getOrderList(OrderSearchRequestDTO model, HttpServletRequest httpServlet) {
        var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServlet)));
        if(user != null) {
            int userId = user.getId();
            if(user.getAppUserRoles().contains(AppUserRole.ROLE_SUPER_ADMIN)) {
                userId = -1;
            }

            List<Object[]> queryResult = orderRepository.getListOrder(model.getKeySearch(), new Date(model.getFromDate()), new Date(model.getToDate()), model.getStatusId(), userId);
            List<OrderSearchResponseDTO> result = new ArrayList<>();
            queryResult.forEach(object -> {
                OrderSearchResponseDTO inputType = new OrderSearchResponseDTO();
                inputType.setId(safeToInt(object[0]));
                inputType.setOrderNumber(safeToString(object[1]));
                inputType.setOrderStatusId(safeToInt(object[2]));
                inputType.setCreatedAt((Timestamp) object[3]);
                inputType.setTotal(safeToDouble(object[4]));
                inputType.setFullName(safeToString(object[5]));
                inputType.setPhone(safeToString(object[6]));
                result.add(inputType);
            });
            return result;
        }
        return null;
    }

    @Override
    public OrderDTO getOne(Integer id) {
        Optional<OrderDTO> optionalOrderDTO = orderRepository.findById(id);
        return optionalOrderDTO.orElse(null);
    }

    @Override
    public Boolean rateOrder(RateRequestDTO body) {
        try{
            EvaluateDTO evaluateDTO = new EvaluateDTO();
            evaluateDTO.setRate(body.getRate());
            evaluateDTO.setOrderId(body.getOrderId());
            evaluateDTO.setContent(body.getContent());
            evaluateDTO.setRate(body.getRate());
            rateRepository.save(evaluateDTO);
        }catch (Exception ex) {
            throw  ex;
        }
        return true;
    }

    @Override
    public List<EvaluateDTO> getRateOrder(Integer orderId) {
        return rateRepository.findAllByOrderIdIs(orderId);
    }

    private void sendFCM(List<AppUser> listUser, String body, String title){
        if (listUser != null) {
            for (var item : listUser) {
                List<CustomerDeviceTokenDTO> list = customerDeviceTokenRepository.getAllByUserIdIsAndDeviceTokenNotNull(item.getId());
                if(list != null) {
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
    }

    private void sendOneFCM(int userId, String body, String title){
        List<CustomerDeviceTokenDTO> list = customerDeviceTokenRepository.getAllByUserIdIsAndDeviceTokenNotNull(userId);
        if(list != null) {
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
