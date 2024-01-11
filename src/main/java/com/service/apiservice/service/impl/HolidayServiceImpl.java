package com.service.apiservice.service.impl;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.*;
import com.service.apiservice.repository.*;
import com.service.apiservice.service.HolidayService;
import com.service.apiservice.service.OrderService;
import com.service.apiservice.utils.DataUtil;
import com.service.apiservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

import static com.service.apiservice.utils.DataUtil.isObjectNull;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public List<HolidayDTO> getList(){
        return holidayRepository.getAllByStatusIsTrue();
    }
}
