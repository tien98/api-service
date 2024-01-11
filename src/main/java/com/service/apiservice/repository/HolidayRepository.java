package com.service.apiservice.repository;

import com.service.apiservice.model.HolidayDTO;
import com.service.apiservice.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayDTO, Integer> {
    List<HolidayDTO> getAllByStatusIsTrue();
}
