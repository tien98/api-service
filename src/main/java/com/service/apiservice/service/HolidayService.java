package com.service.apiservice.service;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.HolidayDTO;
import com.service.apiservice.model.OrderDTO;

import javax.servlet.http.HttpServlet;
import java.util.List;

public interface HolidayService {
    List<HolidayDTO>  getList();
}
