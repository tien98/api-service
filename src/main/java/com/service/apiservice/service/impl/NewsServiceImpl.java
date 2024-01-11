package com.service.apiservice.service.impl;

import com.service.apiservice.dto.DetailNotificationDTO;
import com.service.apiservice.dto.NewResponseDTO;
import com.service.apiservice.model.AppUserRole;
import com.service.apiservice.model.NewsDTO;
import com.service.apiservice.model.NotificationDTO;
import com.service.apiservice.model.OrderDTO;
import com.service.apiservice.repository.*;
import com.service.apiservice.security.JwtTokenProvider;
import com.service.apiservice.service.FileService;
import com.service.apiservice.service.NewsService;
import com.service.apiservice.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    FileService fileService;

    @Autowired
    ModelMapper modelMapper;
    public Map<String, Object> getAll(Pageable pageable, int type) throws IOException {
        Map<String, Object> response = new HashMap<>();
        List<NewsDTO> newsDTOS = new ArrayList<NewsDTO>();
        List<NewResponseDTO> newsDTO2S = new ArrayList<NewResponseDTO>();
        Page<NewsDTO> pageNews;
        pageNews = newsRepository.findAllByTypeIs(pageable, type);
        newsDTOS = pageNews.getContent();
        for (NewsDTO newsDTO : newsDTOS) {
            NewResponseDTO newResponseDTO = modelMapper.map(newsDTO, NewResponseDTO.class);
            newResponseDTO.setNewsImage(fileService.getFile(newsDTO.getNewsImage()));
            newsDTO2S.add(newResponseDTO);
        }
        response.put("news", newsDTO2S);
        response.put("currentPage", pageNews.getNumber());
        response.put("totalItems", pageNews.getTotalElements());
        response.put("totalPages", pageNews.getTotalPages());
        return response;
    }
}
