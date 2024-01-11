package com.service.apiservice.service;

import com.service.apiservice.dto.DetailNotificationDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface NewsService {
    Map<String, Object> getAll(Pageable pageable, int type) throws IOException;
}
