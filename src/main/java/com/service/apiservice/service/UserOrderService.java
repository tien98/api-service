package com.service.apiservice.service;

import com.service.apiservice.model.UserOrderDTO;

public interface UserOrderService {
    String saveUserOrder(UserOrderDTO order);
}
