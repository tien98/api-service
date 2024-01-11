package com.service.apiservice.repository;

import com.service.apiservice.model.MenuDTO;

public interface MenuRepository {
    Boolean saveMenu(MenuDTO menuDTO);
}
