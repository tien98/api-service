package com.service.apiservice.service;

import com.service.apiservice.dto.OrderSearchRequestDTO;
import com.service.apiservice.dto.OrderSearchResponseDTO;
import com.service.apiservice.dto.UpdateStatusRequestDTO;
import com.service.apiservice.model.CustomerDeviceTokenDTO;
import com.service.apiservice.model.OrderDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerDeviceTokenService {
    Boolean save(CustomerDeviceTokenDTO order);
    Boolean update(CustomerDeviceTokenDTO model);
    Boolean updateByImei(CustomerDeviceTokenDTO model);
}
