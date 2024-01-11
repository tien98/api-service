package com.service.apiservice.service.impl;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.OrderSearchResponseDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.*;
import com.service.apiservice.repository.*;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.service.ContactService;
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
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Override
    @Transactional
    public Boolean save(ContactDTO model){
        try{
            contactRepository.save(model);
           return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
