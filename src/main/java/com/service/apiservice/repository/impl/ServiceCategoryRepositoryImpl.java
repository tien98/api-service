package com.service.apiservice.repository.impl;

import com.service.apiservice.model.ServiceCategoryDTO;
import com.service.apiservice.repository.ServiceCategoryCustomRepository;
import com.service.apiservice.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

@Repository
@Slf4j
public class ServiceCategoryRepositoryImpl implements ServiceCategoryCustomRepository {
    @Autowired
    DataSource dataSource;
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void test(ServiceCategoryDTO person) {
        em.refresh(person);
    }
}
