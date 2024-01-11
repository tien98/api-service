package com.service.apiservice.repository;

import com.service.apiservice.model.NewsDTO;
import com.service.apiservice.model.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsDTO, Integer> {
    Page<NewsDTO> findAllByTypeIs(Pageable pageable, int type);
}
