package com.service.apiservice.service.impl;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.OrderSearchResponseDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.*;
import com.service.apiservice.repository.*;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.service.CustomerDeviceTokenService;
import com.service.apiservice.service.OrderService;
import com.service.apiservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.service.apiservice.utils.DataUtil.*;

@Service
@Transactional
public class CustomerDeviceTokenServiceImpl implements CustomerDeviceTokenService {
    @Autowired
    CustomerDeviceTokenRepository customerDeviceTokenRepository;

    @Transactional
    @Override
    public Boolean save(CustomerDeviceTokenDTO model){
        try{
            String imei = model.getImei();
            List<CustomerDeviceTokenDTO> listImei = customerDeviceTokenRepository.getAllByImeiEquals(imei);
            if(listImei.isEmpty()) {
                customerDeviceTokenRepository.save(model);
            }else {
                for (var item : listImei) {
                    item.setDeviceToken(model.getDeviceToken());
                    customerDeviceTokenRepository.save(item);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    @Override
    public Boolean update(CustomerDeviceTokenDTO model){
        try{
            CustomerDeviceTokenDTO row = customerDeviceTokenRepository.findAllByImeiEqualsAndUserIdIs(model.getImei(), model.getUserId());
            if(row == null) {
                customerDeviceTokenRepository.save(model);
            }else{
                row.setDeviceToken(model.getDeviceToken());
                customerDeviceTokenRepository.save(row);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    @Override
    public Boolean updateByImei(CustomerDeviceTokenDTO model) {
        try{
            List<CustomerDeviceTokenDTO> rows = customerDeviceTokenRepository.getAllByImeiEquals(model.getImei());
            for (var item : rows) {
                item.setDeviceToken(model.getDeviceToken());
                customerDeviceTokenRepository.save(item);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }
}
