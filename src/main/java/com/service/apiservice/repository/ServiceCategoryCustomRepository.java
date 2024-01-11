package com.service.apiservice.repository;

import com.service.apiservice.model.ServiceCategoryDTO;
import org.springframework.stereotype.Repository;

public interface ServiceCategoryCustomRepository {
    void test(ServiceCategoryDTO person);
}
