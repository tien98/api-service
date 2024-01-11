package com.service.apiservice.repository;

import com.service.apiservice.dto.OrderSearchResponseDTO;
import com.service.apiservice.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDTO, Integer> {
    @Procedure("ORDER_SRH")
    List<Object[]> getListOrder(String keySearch, Date fromDate, Date toDate, Integer statusId, Integer userId);
}
