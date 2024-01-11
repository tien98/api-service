package com.service.apiservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import com.service.apiservice.config.ResponseData;
import com.service.apiservice.exception.BusinessException;
import com.service.apiservice.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@Slf4j
public class RestApiUtils {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private final String API = ":API: ";
    private final String BODY = ":BODY: ";
    private final String RESPONSE = ":RESPONSE: ";
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private final String KEY_SERVER_FCM = "key=AAAAnt153uA:APA91bEDSRWabHCzHAA0NO54Jl94VYvcaZcvvpAy4eaivxELnc4K-Q6_Rwq0j0d8_SxK2L2UurEe6JiCyHJnFANkwx44BvCltxaU36_8-oH26c92EH82FCzVflLYLGooa4vMsG2J7-q3";

    private String genStringLog() {
        String stringLog = StringUtils.trimToEmpty(API);
        String strDate = DateUtil.date2StringByPattern(Calendar.getInstance().getTime(), "HH-mm-ss.SSS");
        stringLog = stringLog + ":" + StringUtils.trimToEmpty(strDate);
        return stringLog;
    }

    public <T> T getForObject(String url, Map<String, Object> params, Class<T> clazz, boolean isMustLog) {
        String strLog = genStringLog();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        params.forEach((key, value) -> uriBuilder.queryParam(key, String.valueOf(value)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, KEY_SERVER_FCM);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        if (isMustLog) {
            log.info(strLog + API + uriBuilder.toUriString());
        }

        ResponseEntity<ResponseData> response;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        T responseObject = null;
        try {
            response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, httpEntity, ResponseData.class);
            if (isMustLog) {
                log.info(strLog + RESPONSE + objectMapper.writeValueAsString(response));
            }
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode()) {
                ResponseData responseData = response.getBody();
                if (responseData != null && Boolean.TRUE.equals(responseData.getSuccess()) && responseData.getContent() != null) {
                    responseObject = objectMapper.readValue(objectMapper.writeValueAsString(responseData.getContent()), clazz);
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(e.getMessage()), e);
        } catch (HttpClientErrorException exception) {
            Gson gson = new Gson();
            try {
                throw gson.fromJson(exception.getResponseBodyAsString(), BusinessException.class);
            } catch (Exception exc) {
                if (exc instanceof BusinessException) {
                    throw exc;
                } else {
                    throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(exc.getMessage()), exc);
                }
            }
        }
        return responseObject;
    }

    public <T> T postForObject(String url, Object body, Class<T> clazz, boolean isMustLog) {
        String strLog = genStringLog();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, KEY_SERVER_FCM);

        Gson gson = new Gson();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);

        ResponseEntity<ResponseData> response;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T responseObject = null;
        try {
            if (isMustLog) {
                log.info(strLog + API + url);
                log.info(strLog + BODY + objectMapper.writeValueAsString(body));
            }
            response = restTemplate.postForEntity(url, httpEntity, ResponseData.class);
            if (isMustLog) {
                log.info(strLog + RESPONSE + objectMapper.writeValueAsString(response));
            }
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode()) {
                ResponseData responseData = response.getBody();
                if (responseData != null && Boolean.TRUE.equals(responseData.getSuccess()) && responseData.getContent() != null) {
                    responseObject = objectMapper.readValue(objectMapper.writeValueAsString(responseData.getContent()), clazz);
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(e.getMessage()), e);
        } catch (HttpClientErrorException exception) {
            try {
                throw gson.fromJson(exception.getResponseBodyAsString(), BusinessException.class);
            } catch (Exception exc) {
                if (exc instanceof BusinessException) {
                    throw exc;
                } else {
                    throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(exc.getMessage()), exc);
                }
            }
        }
        return responseObject;
    }
    public <T> List<T> getForList(String url, MultiValueMap<String, String> params, Class<T> clazz, boolean isMustLog) {
        String strLog = genStringLog();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, KEY_SERVER_FCM);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        if (isMustLog) {
            log.info(strLog + API + uriBuilder.toUriString());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResponseEntity<ResponseData> response;
        List<T> responseObject = new ArrayList<>();
        try {
            response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, httpEntity, ResponseData.class);
            if (isMustLog) {
                log.info(strLog + RESPONSE + objectMapper.writeValueAsString(response));
            }
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode()) {
                ResponseData responseData = response.getBody();
                if (responseData != null && Boolean.TRUE.equals(responseData.getSuccess()) && responseData.getContent() != null) {
                    CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
                    responseObject = objectMapper.readValue(objectMapper.writeValueAsString(responseData.getContent()), javaType);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(e.getMessage()), e);
        } catch (HttpClientErrorException exception) {
            Gson gson = new Gson();
            try {
                throw gson.fromJson(exception.getResponseBodyAsString(), BusinessException.class);
            } catch (Exception exc) {
                if (exc instanceof BusinessException) {
                    throw exc;
                } else {
                    throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(exc.getMessage()), exc);
                }
            }
        }
        return responseObject;
    }

    public <T> List<T> postForList(String url, Map<String, Object> body, Class<T> clazz, boolean isMustLog) {
        String strLog = genStringLog();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, KEY_SERVER_FCM);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

//        log.info(strLog + url);
        Gson gson = new Gson();
//        log.info(strLog + gson.toJson(httpEntity));

        ResponseEntity<ResponseData> response;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<T> responseObject = new ArrayList<>();
        try {
            if (isMustLog) {
                log.info(strLog + API + url);
                log.info(strLog + BODY + objectMapper.writeValueAsString(body));
            }
            response = restTemplate.postForEntity(url, httpEntity, ResponseData.class);
            if (isMustLog) {
                log.info(strLog + RESPONSE + objectMapper.writeValueAsString(response));
            }
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode()) {
                ResponseData responseData = response.getBody();
                if (responseData != null && responseData.getContent() != null && Boolean.TRUE.equals(responseData.getSuccess())) {
                    CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
                    responseObject = objectMapper.readValue(objectMapper.writeValueAsString(responseData.getContent()), javaType);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(e.getMessage()), e);
        } catch (HttpClientErrorException exception) {
            try {
                throw gson.fromJson(exception.getResponseBodyAsString(), BusinessException.class);
            } catch (Exception exc) {
                if (exc instanceof BusinessException) {
                    throw exc;
                } else {
                    throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(exc.getMessage()), exc);
                }
            }
        }
        return responseObject;
    }

    public <T> T postBodyObject(String url, Object body, Class<T> clazz, boolean isMustLog) {
        String strLog = genStringLog();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, KEY_SERVER_FCM);

        Gson gson = new Gson();
        HttpEntity<String> httpEntity = new HttpEntity<>((body instanceof String) ? body.toString() : gson.toJson(body), headers);

//        ResponseEntity<ResponseData> response;
        ResponseEntity<Object> response;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T responseObject = null;
        try {
            if (isMustLog) {
                log.info(strLog + API + url);
                log.info(strLog + BODY + objectMapper.writeValueAsString(body));
            }
//            response = restTemplate.postForEntity(url, httpEntity, ResponseData.class);
            response = restTemplate.postForEntity(url, httpEntity, Object.class);
            if (isMustLog) {
                log.info(strLog + RESPONSE + objectMapper.writeValueAsString(response));
            }
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode()) {
                responseObject = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody()), clazz);
//                ResponseData responseData = response.getBody();
//                if (responseData != null && responseData.getSuccess() && responseData.getContent() != null) {
//                    responseObject = objectMapper.readValue(objectMapper.writeValueAsString(responseData.getContent()), clazz);
//                } else {
//                    if (responseData != null) {
//                        BusinessException ex = new BusinessException(DataUtil.safeToString(responseData.getContent()), responseData.getContent());
//                        ex.setHttpStatus(HttpStatus.OK);
//                        throw ex;
//                    }
//                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(e.getMessage()), e);
        } catch (HttpClientErrorException exception) {
            try {
                throw gson.fromJson(exception.getResponseBodyAsString(), BusinessException.class);
            } catch (Exception exc) {
                if (exc instanceof BusinessException) {
                    throw exc;
                } else {
                    throw new RuntimeException(strLog + ":" + StringUtils.trimToEmpty(exc.getMessage()), exc);
                }
            }
        }
        return responseObject;
    }
}
