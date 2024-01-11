package com.service.apiservice.service;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.OrderSearchResponseDTO;
import com.service.apiservice.dto.RateRequestDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.EvaluateDTO;
import com.service.apiservice.model.OrderDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    Boolean saveOrder(OrderDTO order);
    Boolean updateStatus(UpdateStatusRequestDTO model);
    List<OrderSearchResponseDTO>  getOrderList(OrderSearchRequestDTO model, HttpServletRequest httpServlet);
    OrderDTO getOne(Integer id);
    Boolean rateOrder(RateRequestDTO body);
    List<EvaluateDTO> getRateOrder(Integer orderId);
}
