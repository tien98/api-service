package com.service.apiservice.repository.impl;

import com.service.apiservice.model.AppUser;
import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.model.UserNotificationDTO;
import com.service.apiservice.repository.NotificationCustomRepository;
import com.service.apiservice.repository.NotificationRepository;
import com.service.apiservice.repository.ServiceCategoryCustomRepository;
import com.service.apiservice.repository.UserNotificationRepository;
import com.service.apiservice.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
public class NotificationsRepositoryImpl implements NotificationCustomRepository {
    @Autowired
    DataSource dataSource;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserNotificationRepository userNotificationRepository;
    @Override
    @Transactional
    public Boolean saveNotify(NotificationDTO notificationDTO, List<AppUser> appUserList) {
        try{
            NotificationDTO notify = notificationRepository.saveAndFlush(notificationDTO);
            Integer idNotify = notify.getId();
            for (var item : appUserList){
                UserNotificationDTO userNotificationDTO = new UserNotificationDTO();
                userNotificationDTO.setNotifyId(idNotify);
                userNotificationDTO.setCustomerId(item.getId());
                userNotificationDTO.setStatus(true);
                userNotificationRepository.save(userNotificationDTO);
            }
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean sendNotify(NotificationDTO notificationDTO, Integer userId) {
        try{
            NotificationDTO notify = notificationRepository.saveAndFlush(notificationDTO);
            Integer idNotify = notify.getId();

            UserNotificationDTO userNotificationDTO = new UserNotificationDTO();
            userNotificationDTO.setNotifyId(idNotify);
            userNotificationDTO.setCustomerId(userId);
            userNotificationDTO.setStatus(true);
            userNotificationRepository.save(userNotificationDTO);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }
}
