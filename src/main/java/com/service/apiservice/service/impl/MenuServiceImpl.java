package com.service.apiservice.service.impl;

import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.repository.MenuRepository;
import com.service.apiservice.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;
    public Boolean saveMenu(MenuDTO menuDTO){
        return menuRepository.saveMenu(menuDTO);
    }
}
